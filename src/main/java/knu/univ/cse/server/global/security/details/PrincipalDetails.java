package knu.univ.cse.server.global.security.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;
import knu.univ.cse.server.domain.service.student.StudentService;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PrincipalDetails implements OAuth2User {
    private final Student student;
    private final OAuthUserInfo oAuthUserInfo;
    private final Map<String, Object> attributes;

    @Builder
    public PrincipalDetails(Student student, OAuthUserInfo oAuthUserInfo, Map<String, Object> attributes) {
        this.student = student;
        this.oAuthUserInfo = oAuthUserInfo;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(student.getRole().name()));
        return authorities;
    }

    @Override
    public String getName() {
        return student.getStudentName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public static PrincipalDetails buildPrincipalDetails(StudentService studentService, OAuthUserInfo oAuthUserInfo, OAuth2User oAuth2User) {
        Student student = studentService.isOAuth2UserInfoConnectedToStudent(oAuthUserInfo)
            ? studentService.findStudentByOAuth2UserInfo(oAuthUserInfo)
            : null;

        return PrincipalDetails.builder()
            .student(student)
            .oAuthUserInfo(oAuthUserInfo)
            .attributes(oAuth2User != null ? oAuth2User.getAttributes() : null)
            .build();
    }
}
