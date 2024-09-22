package knu.univ.cse.server.global.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import knu.univ.cse.server.domain.service.student.StudentService;
import knu.univ.cse.server.global.jwt.provider.JwtTokenValidator;
import knu.univ.cse.server.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {
    private final StudentService studentService;
    private final JwtTokenValidator jwtTokenValidator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        if ("OPTIONS".equalsIgnoreCase(servletRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String token = JwtTokenValidator.resolveToken(servletRequest);
        log.info("Token: {}", token);
        if (token != null && jwtTokenValidator.validateToken(token)) {
            try {
                log.info("Token is valid");
                Authentication authentication = jwtTokenValidator.getAuthentication(token, studentService);
                log.info("Authentication: {}", ((PrincipalDetails) authentication.getPrincipal()).getOAuthUserInfo().getEmail());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Log the exception and proceed without setting authentication
                log.error("Authentication failed: {}", e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
