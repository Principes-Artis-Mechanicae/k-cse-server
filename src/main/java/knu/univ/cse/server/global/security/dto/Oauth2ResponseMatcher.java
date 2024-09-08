package knu.univ.cse.server.global.security.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Oauth2ResponseMatcher {
    public Oauth2ResponseDto matcher(String registrationId, OAuth2User oAuth2User) {
        if (registrationId.equals("google")) {
            return new GoogleResponseDto(oAuth2User.getAttributes());
        }
        else return null;
    }
}
