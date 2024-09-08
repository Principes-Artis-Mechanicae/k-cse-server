package knu.univ.cse.server.global.jwt.dto;

import jakarta.servlet.http.Cookie;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Token(
	@NotNull String grantType,
	@NotNull TokenType tokenType,
	@NotNull String token,
	@NotNull Cookie cookie
	) {}
