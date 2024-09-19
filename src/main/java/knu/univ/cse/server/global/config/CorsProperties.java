package knu.univ.cse.server.global.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
	private List<String> allowedOrigins;
	private List<String> allowedMethods;
	private boolean allowCredentials;
}
