package knu.univ.cse.server.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Properties {
	private String redirectUri;
}
