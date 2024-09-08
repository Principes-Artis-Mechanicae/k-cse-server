package knu.univ.cse.server.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import knu.univ.cse.server.domain.student.service.StudentService;
import knu.univ.cse.server.global.jwt.provider.JwtTokenValidator;
import knu.univ.cse.server.global.security.details.PrincipalOauth2UserService;
import knu.univ.cse.server.global.security.filter.JwtAuthorizationFilter;
import knu.univ.cse.server.global.security.handler.Oauth2SuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenValidator jwtTokenValidator;
	private final StudentService studentService;
	private final PrincipalOauth2UserService principalOauth2UserService;
	private final Oauth2SuccessHandler oauth2SuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity httpSecurity
	) throws Exception {
		httpSecurity
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable);

		httpSecurity
			.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.addFilterBefore(new JwtAuthorizationFilter(studentService, jwtTokenValidator), UsernamePasswordAuthenticationFilter.class)
			.httpBasic(AbstractHttpConfigurer::disable);

		httpSecurity
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
					.userService(principalOauth2UserService))
				.successHandler(oauth2SuccessHandler)
				.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
					.baseUri("/oauth2/authorize"))
			);
		return httpSecurity.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(
			"http://localhost:3000"
		));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
