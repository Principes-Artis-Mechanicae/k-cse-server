package knu.univ.cse.server.api.student.dto;

import knu.univ.cse.server.domain.model.student.Student;
import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;
import lombok.Builder;

@Builder
public record OauthReadDto(
	String email, String provider,
	String providerId, boolean isConnectedToStudent,
	StudentReadDto student
) {
	public static OauthReadDto fromEntity(Student student, OAuthUserInfo oAuthUserInfo) {
		boolean isConnectedToStudent = student != null;
		StudentReadDto studentDto = isConnectedToStudent ? StudentReadDto.fromEntity(student) : null;

		return OauthReadDto.builder()
			.email(oAuthUserInfo.getEmail())
			.provider(oAuthUserInfo.getProvider())
			.providerId(oAuthUserInfo.getProviderId())
			.isConnectedToStudent(isConnectedToStudent)
			.student(studentDto)
			.build();
	}
}
