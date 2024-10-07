package knu.univ.cse.server.api.locker.apply._anonymous;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import knu.univ.cse.server.api.locker.apply.dto.ApplyCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReadDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportReadDto;
import knu.univ.cse.server.domain.service.locker.apply.ApplyService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Tag(name = "신청 (사용자)", description = "사용자용 신청 API")
public class ApplyAnonymousController {
	private final ApplyService applyService;

	@PostMapping("/primary")
	@Operation(summary = "1차 신청", description = "사용자는 현재 활성화된 신청 폼에 대해 1차 신청을 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "신청 생성 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 신청 기간 (code: INVALID_APPLY_PERIOD)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)"),
		@ApiResponse(responseCode = "409", description = "이미 신청이 존재함 (code: APPLY_DUPLICATED)"),
		@ApiResponse(responseCode = "400", description = "이미 배정 받은 사람이 신청함 (code: ALREADY_ALLOCATED)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyPrimary(
		@Valid @RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handlePrimaryApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/additional")
	@Operation(summary = "추가 신청", description = "사용자는 현재 활성화된 신청 폼에 대해 추가 신청을 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "신청 생성 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 신청 기간 (code: INVALID_APPLY_PERIOD)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)"),
		@ApiResponse(responseCode = "409", description = "이미 신청이 존재함 (code: APPLY_DUPLICATED)"),
		@ApiResponse(responseCode = "400", description = "이미 배정 받은 사람이 신청함 (code: ALREADY_ALLOCATED)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyAdditional(
		@Valid @RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handleAdditionalApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/replacement")
	@Operation(summary = "사물함 교체 신청 및 고장 신고", description = "사용자는 현재 활성화된 신청 폼에 대해 사물함 교체 신청과 고장 신고를 할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "신청 및 보고서 생성 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 신청 기간 (code: INVALID_APPLY_PERIOD)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)"),
		@ApiResponse(responseCode = "409", description = "이미 신청이 존재함 (code: APPLY_DUPLICATED)"),
		@ApiResponse(responseCode = "404", description = "배정 받지 않은 사람이 신청함 (code: ALLOCATE_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReportReadDto>> applyReplacement(
		@Valid @RequestBody ApplyReportCreateDto requestBody
	) {
		ApplyReportReadDto responseBody = applyService.handleReplacementApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@GetMapping("/{studentNumber}")
	@Operation(summary = "신청 조회", description = "사용자는 자신의 학번으로 신청 상태를 조회할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 조회 성공"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> getApply(
		@PathVariable String studentNumber
	) {
		ApplyReadDto responseBody = applyService.getApplyByStudentNumber(studentNumber);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
