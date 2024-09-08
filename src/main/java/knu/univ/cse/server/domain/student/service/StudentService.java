package knu.univ.cse.server.domain.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.student.entity.OAuth2UserInfo;
import knu.univ.cse.server.domain.student.entity.Student;
import knu.univ.cse.server.domain.student.repository.OAuth2UserInfoRepository;
import knu.univ.cse.server.domain.student.repository.StudentRepository;
import knu.univ.cse.server.global.exception.CustomAssert;
import knu.univ.cse.server.global.security.dto.Oauth2ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@Getter
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final OAuth2UserInfoRepository oAuth2UserInfoRepository;

	private OAuth2UserInfo saveOauth2UserInfo(Oauth2ResponseDto responseDto) {
        return oAuth2UserInfoRepository.save(
            OAuth2UserInfo.builder()
                .email(responseDto.getEmail())
                .provider(responseDto.getProvider())
                .providerId(responseDto.getProviderId())
                .build()
        );
    }

    @Transactional
    public OAuth2UserInfo saveOrReadOauth2UserInfo(Oauth2ResponseDto responseDto) {
        if (oAuth2UserInfoRepository.existsByEmail(responseDto.getEmail()))
            return oAuth2UserInfoRepository.findByEmail(responseDto.getEmail());
        else
            return saveOauth2UserInfo(responseDto);
    }

    public boolean isOAuth2UserInfoConnectedToStudent(OAuth2UserInfo oAuth2UserInfo) {
        return studentRepository.existsByOAuth2UserInfo(oAuth2UserInfo);
    }

    public OAuth2UserInfo findOAuth2UserInfoByEmail(String email) {
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoRepository.findByEmail(email);
        CustomAssert.notFound(oAuth2UserInfo, OAuth2UserInfo.class);
        return oAuth2UserInfo;
    }

    public Student findStudentByOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo) {
        Student student = studentRepository.findByOAuth2UserInfo(oAuth2UserInfo);
        CustomAssert.notFound(student, Student.class);
        return student;
    }
}
