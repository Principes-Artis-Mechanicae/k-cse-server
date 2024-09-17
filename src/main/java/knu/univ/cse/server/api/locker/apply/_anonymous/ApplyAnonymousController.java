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
@Tag(name = "Anonymous Apply", description = "익명 사용자가 사용하는 신청 API 입니다.")
public class ApplyAnonymousController {
	private final ApplyService applyService;

	@Operation(summary = "1차 모집 신청 생성")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "1차 모집 신청이 성공적으로 제출되었습니다."),
		@ApiResponse(responseCode = "409", description = "중복된 신청이 감지되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PostMapping("/primary")
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyPrimary(
		@RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handlePrimaryApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@Operation(summary = "추가 모집 신청 생성")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "추가 모집 신청이 성공적으로 제출되었습니다."),
		@ApiResponse(responseCode = "409", description = "중복된 신청이 감지되었습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PostMapping("/additional")
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyAdditional(
		@RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handleAdditionalApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@Operation(summary = "교체 신청 및 보고서 제출")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "교체 신청이 성공적으로 제출되었습니다."),
		@ApiResponse(responseCode = "409", description = "중복된 신청이 감지되었습니다."),
		@ApiResponse(responseCode = "404", description = "관련 신청을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PostMapping("/replacement")
	public ResponseEntity<ApiSuccessResult<ApplyReportReadDto>> applyReplacement(
		@RequestBody ApplyReportCreateDto requestBody
	) {
		ApplyReportReadDto responseBody = applyService.handleReplacementApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@Operation(summary = "학번으로 신청 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청을 성공적으로 조회했습니다."),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@GetMapping("/{studentNumber}")
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> getApply(
		@PathVariable String studentNumber
	) {
		ApplyReadDto responseBody = applyService.getApplyByStudentNumber(studentNumber);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
