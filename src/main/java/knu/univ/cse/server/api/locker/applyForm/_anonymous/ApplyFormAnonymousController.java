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
@Tag(name = "신청 폼 (사용자)", description = "사용자용 신청 폼 API")
public class ApplyFormAnonymousController {
	private final ApplyFormService applyFormService;

	@GetMapping("/now")
	@Operation(summary = "현재 신청 폼 조회", description = "사용자는 현재 활성화된 신청 폼을 조회할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "현재 신청 폼 조회 성공"),
		@ApiResponse(responseCode = "404", description = "활성화된 신청 폼을 찾을 수 없음 (code: APPLY_FORM_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> getNowForm() {
		ApplyFormReadDto responseBody = applyFormService.getNowApplyForm();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
