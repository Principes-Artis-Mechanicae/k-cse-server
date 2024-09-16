package knu.univ.cse.server.global.security.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import knu.univ.cse.server.domain.model.student.oauth2.OAuth2UserInfo;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.service.student.StudentService;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PrincipalDetails implements OAuth2User {
    private final Student student;
    private final OAuth2UserInfo oAuth2UserInfo;
    private final Map<String, Object> attributes;

    @Builder
    public PrincipalDetails(Student student, OAuth2UserInfo oAuth2UserInfo, Map<String, Object> attributes) {
        this.student = student;
        this.oAuth2UserInfo = oAuth2UserInfo;
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

    public static PrincipalDetails buildPrincipalDetails(StudentService studentService, OAuth2UserInfo oAuth2UserInfo, OAuth2User oAuth2User) {
        Student student = studentService.isOAuth2UserInfoConnectedToStudent(oAuth2UserInfo)
            ? studentService.findStudentByOAuth2UserInfo(oAuth2UserInfo)
            : null;

        return PrincipalDetails.builder()
            .student(student)
            .oAuth2UserInfo(oAuth2UserInfo)
            .attributes(oAuth2User != null ? oAuth2User.getAttributes() : null)
            .build();
    }
}
