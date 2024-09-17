package knu.univ.cse.server.api.locker.applyForm._executive;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormCreateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
@Tag(name = "Executive Apply Form", description = "집행부가 사용하는 신청 폼 관련 API 입니다.")
public class ApplyFormExecutiveController {
	private final ApplyFormService applyFormService;

	@Operation(summary = "새로운 신청 폼 생성")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "신청 폼이 성공적으로 생성되었습니다."),
		@ApiResponse(responseCode = "409", description = "신청 폼이 이미 존재합니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PostMapping
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> createForm(
		@RequestBody ApplyFormCreateDto requestBody
	) {
		ApplyFormReadDto responseBody = applyFormService.createApplyForm(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@Operation(summary = "기존 신청 폼 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 폼이 성공적으로 수정되었습니다."),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PutMapping("/{year}/{semester}")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> updateForm(
		@PathVariable Integer year,
		@PathVariable Integer semester,
		@RequestBody ApplyFormUpdateDto requestBody
	) {
		ApplyFormReadDto updatedForm = applyFormService.updateApplyForm(year, semester, requestBody);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, updatedForm));
	}

	@Operation(summary = "기존 신청 폼 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "신청 폼이 성공적으로 삭제되었습니다."),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@DeleteMapping("/{year}/{semester}")
	public ResponseEntity<ApiSuccessResult<Void>> deleteForm(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		applyFormService.deleteApplyForm(year, semester);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(ApiUtil.success(HttpStatus.NO_CONTENT));
	}

	@Operation(summary = "모든 신청 폼 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 폼 목록을 성공적으로 조회했습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@GetMapping
	public ResponseEntity<ApiSuccessResult<List<ApplyFormReadDto>>> getAllForms() {
		List<ApplyFormReadDto> forms = applyFormService.getAllApplyForms();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, forms));
	}

	@Operation(summary = "특정 연도 및 학기에 해당하는 신청 폼 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 폼을 성공적으로 조회했습니다."),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@GetMapping("/{year}/{semester}")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> getFormByYearAndSemester(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		ApplyFormReadDto form = applyFormService.getApplyForm(year, semester);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, form));
	}

	@Operation(summary = "특정 연도 및 학기의 신청 폼 상태 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 폼 상태가 성공적으로 수정되었습니다."),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없습니다."),
		@ApiResponse(responseCode = "409", description = "다른 활성화된 신청 폼이 이미 존재합니다."),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
	})
	@PatchMapping("/{year}/{semester}")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> updateFormStatus(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		ApplyFormReadDto updatedForm = applyFormService.updateApplyFormStatus(year, semester);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, updatedForm));
	}
}
