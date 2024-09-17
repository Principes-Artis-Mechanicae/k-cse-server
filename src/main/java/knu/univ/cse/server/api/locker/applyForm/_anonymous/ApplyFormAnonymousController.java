package knu.univ.cse.server.api.locker.applyForm._anonymous;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Tag(name = "Anonymous Apply Form", description = "익명의 사용자가 사용하는 신청 폼 관련 API 입니다.")
public class ApplyFormAnonymousController {
	private final ApplyFormService applyFormService;

	@Operation(summary = "현재 활성화된 신청 폼 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "현재 신청 폼을 성공적으로 조회했습니다."),
		@ApiResponse(responseCode = "404", description = "활성화된 신청 폼을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@GetMapping("/now")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> getNowForm() {
		ApplyFormReadDto responseBody = applyFormService.getNowApplyForm();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
