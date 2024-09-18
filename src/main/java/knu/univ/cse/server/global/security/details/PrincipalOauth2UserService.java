package knu.univ.cse.server.global.security.details;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;
import knu.univ.cse.server.domain.service.student.StudentService;
import knu.univ.cse.server.global.security.dto.Oauth2ResponseDto;
import knu.univ.cse.server.global.security.dto.Oauth2ResponseMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	private final StudentService studentService;
	private final Oauth2ResponseMatcher oauth2ResponseMatcher;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		Oauth2ResponseDto oauth2Response = oauth2ResponseMatcher.matcher(registrationId, oAuth2User);
		OAuthUserInfo oAuthUserInfo = studentService.saveOrReadOauth2UserInfo(oauth2Response);

		return PrincipalDetails.buildPrincipalDetails(studentService, oAuthUserInfo, oAuth2User);
	}
}
