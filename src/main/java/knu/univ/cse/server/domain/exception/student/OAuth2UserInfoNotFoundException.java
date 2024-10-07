package knu.univ.cse.server.domain.exception.student;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class OAuth2UserInfoNotFoundException extends NotFoundException {
	private static final String code = "OAUTH2_USER_INFO_NOT_FOUND";

	public OAuth2UserInfoNotFoundException() {
		super(code);
	}
}
