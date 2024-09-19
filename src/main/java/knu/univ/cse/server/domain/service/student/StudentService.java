package knu.univ.cse.server.domain.service.student;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.exception.student.OAuth2UserInfoNotFoundException;
import knu.univ.cse.server.domain.exception.student.StudentNotFoundException;
import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;
import knu.univ.cse.server.domain.persistence.OAuth2UserInfoRepository;
import knu.univ.cse.server.domain.persistence.StudentRepository;
import knu.univ.cse.server.global.security.dto.Oauth2ResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;
    private final OAuth2UserInfoRepository oAuth2UserInfoRepository;

    /**
     * OAuth2 사용자 정보를 저장하거나 기존 정보를 반환합니다.
     *
     * @param responseDto OAuth2 응답 DTO
     * @return 저장되었거나 기존의 OAuth2 사용자 정보 엔티티
     */
    @Transactional
    public OAuthUserInfo saveOrReadOauth2UserInfo(Oauth2ResponseDto responseDto) {
        Optional<OAuthUserInfo> oAuth2UserInfoOptional =
            oAuth2UserInfoRepository.findByEmail(responseDto.getEmail());
        return oAuth2UserInfoOptional.orElseGet(() ->
            oAuth2UserInfoRepository.save(responseDto.toEntity()));
    }

    /**
     * 주어진 OAuth2 사용자 정보가 학생과 연결되어 있는지 확인합니다.
     *
     * @param oAuthUserInfo OAuth2 사용자 정보 엔티티
     * @return 연결되어 있으면 true, 그렇지 않으면 false
     */
    public boolean isOAuth2UserInfoConnectedToStudent(OAuthUserInfo oAuthUserInfo) {
        return studentRepository.existsById(oAuthUserInfo.getId());
    }

    /**
     * 이메일을 통해 OAuth2 사용자 정보를 조회합니다.
     *
     * @param email 조회할 이메일 주소
     * @return 해당 이메일의 OAuth2 사용자 정보 엔티티
     * @throws OAuth2UserInfoNotFoundException "OAUTH2_USER_INFO_NOT_FOUND"
     */
    public OAuthUserInfo findOAuth2UserInfoByEmail(String email) {
        return oAuth2UserInfoRepository.findByEmail(email)
            .orElseThrow(OAuth2UserInfoNotFoundException::new);
    }

    /**
     * OAuth2 사용자 정보를 통해 학생을 조회합니다.
     *
     * @param oAuthUserInfo OAuth2 사용자 정보 엔티티
     * @return 해당 OAuth2 사용자 정보에 연결된 학생 엔티티
     * @throws StudentNotFoundException "STUDENT_NOT_FOUND"
     */
    public Student findStudentByOAuth2UserInfo(OAuthUserInfo oAuthUserInfo) {
        return studentRepository.findStudentById(oAuthUserInfo.getId())
            .orElseThrow(StudentNotFoundException::new);
    }

    /**
     * 학생 이름과 학번을 통해 학생을 조회합니다.
     *
     * @param studentName 학생 이름
     * @param studentNumber 학생 학번
     * @return 해당 이름과 학번을 가진 학생 엔티티
     * @throws StudentNotFoundException "STUDENT_NOT_FOUND"
     */
    public Student findStudentByNameAndNumber(String studentName, String studentNumber) {
        return studentRepository.findByStudentNameAndStudentNumber(studentName, studentNumber)
            .orElseThrow(StudentNotFoundException::new);
    }

    /**
     * 학번을 통해 학생을 조회합니다.
     *
     * @param studentNumber 학생 학번
     * @return 해당 학번을 가진 학생 엔티티
     * @throws StudentNotFoundException "STUDENT_NOT_FOUND"
     */
    public Student findStudentByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber)
            .orElseThrow(StudentNotFoundException::new);
    }



}
