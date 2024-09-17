package knu.univ.cse.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "KNU CSE API",
		version = "1.0",
		description = "학생 사물함 관리 시스템 API 문서"
	)
)
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(new io.swagger.v3.oas.models.info.Info().title("KNU CSE API").version("1.0"));
	}
}
