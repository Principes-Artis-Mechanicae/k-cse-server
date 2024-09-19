package knu.univ.cse.server.global.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import knu.univ.cse.server.global.config.OAuth2Properties;
import knu.univ.cse.server.global.jwt.dto.Token;
import knu.univ.cse.server.global.jwt.provider.JwtTokenGenerator;
import knu.univ.cse.server.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenGenerator jwtTokenGenerator;
    private final OAuth2Properties oauth2Properties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Token accessToken = jwtTokenGenerator.generateAccessToken(authentication);
        response.addCookie(accessToken.cookie());

        // 사용자와 학생이 연결되어 있는지 확인
        boolean isConnectedToStudent = principalDetails.getStudent() != null;

        // 프론트엔드의 리다이렉트 URL 설정 from configuration
        String redirectUrl = oauth2Properties.getRedirectUri();

        // 필요한 정보를 쿼리 파라미터로 추가
        redirectUrl += "?isConnectedToStudent=" + isConnectedToStudent;

        // 리다이렉트 수행
        response.sendRedirect(redirectUrl);

        // 로그 출력
        log.info("OAuth2 로그인에 성공하였습니다. 이메일 : {}", principalDetails.getOAuthUserInfo().getEmail());
    }
}
