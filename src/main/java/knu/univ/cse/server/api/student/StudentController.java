package knu.univ.cse.server.api.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.univ.cse.server.api.student.dto.OauthReadDto;
import knu.univ.cse.server.domain.service.student.StudentService;
import knu.univ.cse.server.global.security.details.PrincipalDetails;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "학생 개인정보", description = "학생 개인정보 관련 API")
public class StudentController {
	private final StudentService studentService;

	@GetMapping("/me")
	@Operation(summary = "학생 본인의 개인정보 조회", description = "로그인한 학생 본인의 개인정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "학생 본인의 개인정보 조회 성공"),
		@ApiResponse(responseCode = "401", description = "로그인이 필요합니다.")
	})
	public ResponseEntity<ApiSuccessResult<OauthReadDto>> getMe(
		@AuthenticationPrincipal PrincipalDetails principalDetails
	) {
		OauthReadDto responseBody = OauthReadDto.fromEntity(principalDetails.getStudent(), principalDetails.getOAuthUserInfo());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
