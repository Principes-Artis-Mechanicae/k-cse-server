package knu.univ.cse.server.domain.service.student;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.exception.student.OAuth2UserInfoNotFoundException;
import knu.univ.cse.server.domain.exception.student.StudentNotFoundException;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.model.student.oauth2.OAuth2UserInfo;
import knu.univ.cse.server.domain.persistence.OAuth2UserInfoRepository;
import knu.univ.cse.server.domain.persistence.StudentRepository;
import knu.univ.cse.server.global.security.dto.Oauth2ResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final OAuth2UserInfoRepository oAuth2UserInfoRepository;

    @Transactional
    public OAuth2UserInfo saveOrReadOauth2UserInfo(Oauth2ResponseDto responseDto) {
        Optional<OAuth2UserInfo> oAuth2UserInfoOptional =
            oAuth2UserInfoRepository.findByEmail(responseDto.getEmail());
		return oAuth2UserInfoOptional.orElseGet(() ->
            oAuth2UserInfoRepository.save(responseDto.toEntity()));
    }

    public boolean isOAuth2UserInfoConnectedToStudent(OAuth2UserInfo oAuth2UserInfo) {
        return studentRepository.existsById(oAuth2UserInfo.getId());
    }

    public OAuth2UserInfo findOAuth2UserInfoByEmail(String email) {
		return oAuth2UserInfoRepository.findByEmail(email)
            .orElseThrow(OAuth2UserInfoNotFoundException::new);
    }

    public Student findStudentByOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo) {
        return studentRepository.findStudentById(oAuth2UserInfo.getId())
            .orElseThrow(StudentNotFoundException::new);
    }

    public Student findStudentByNameAndNumber(String studentName, String studentNumber) {
        return studentRepository.findByStudentNameAndStudentNumber(studentName, studentNumber)
            .orElseThrow(StudentNotFoundException::new);
    }

    public Student findStudentByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber)
            .orElseThrow(StudentNotFoundException::new);
    }
}
