package knu.univ.cse.server.api.locker.apply._executive;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReadDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyUpdateDto;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.service.locker.apply.ApplyService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
@Tag(name = "신청 관리 (집행부)", description = "집행부용 신청 관리 API")
public class ApplyExecutiveController {
	private final ApplyService applyService;

	@GetMapping("/{year}/{semester}")
	@Operation(summary = "신청 목록 조회", description = "집행부는 특정 연도와 학기의 신청 목록을 조회할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 목록 조회 성공"),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없음 (code: APPLY_FORM_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<List<ApplyReadDto>>> getAppliesByYearAndSemester(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		List<ApplyReadDto> applies = applyService.getAppliesByYearAndSemester(year, semester);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, applies));
	}

	@GetMapping("/{year}/{semester}/status/{status}")
	@Operation(summary = "신청 목록 조회 (상태별)", description = "집행부는 특정 연도, 학기, 상태의 신청 목록을 조회할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 목록 조회 성공"),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없음 (code: APPLY_FORM_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<List<ApplyReadDto>>> getAppliesByYearSemesterAndStatus(
		@PathVariable Integer year,
		@PathVariable Integer semester,
		@PathVariable ApplyStatus status
	) {
		List<ApplyReadDto> applies = applyService.getAppliesByYearSemesterAndStatus(year, semester, status);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, applies));
	}

	@PutMapping("/{year}/{semester}/student/{studentNumber}")
	@Operation(summary = "특정 학생의 신청 수정", description = "집행부는 특정 연도, 학기의 특정 학생(학번)의 신청을 수정할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 수정 성공"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없음 (code: APPLY_FORM_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> updateApplyByStudentNumber(
		@PathVariable Integer year,
		@PathVariable Integer semester,
		@PathVariable String studentNumber,
		@RequestBody ApplyUpdateDto requestBody
	) {
		ApplyReadDto updatedApply = applyService.updateApplyByStudentNumber(year, semester, studentNumber, requestBody);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, updatedApply));
	}

	@DeleteMapping("/{year}/{semester}/student/{studentNumber}")
	@Operation(summary = "특정 학생의 신청 삭제", description = "집행부는 특정 연도, 학기의 특정 학생(학번)의 신청을 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "신청 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "신청 폼을 찾을 수 없음 (code: APPLY_FORM_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<Void>> deleteApplyByStudentNumber(
		@PathVariable Integer year,
		@PathVariable Integer semester,
		@PathVariable String studentNumber
	) {
		applyService.deleteApplyByStudentNumber(year, semester, studentNumber);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(ApiUtil.success(HttpStatus.NO_CONTENT));
	}

	@PutMapping("/{applyId}")
	@Operation(summary = "신청 수정 (ID 기반)", description = "집행부는 Apply ID를 사용하여 특정 신청을 수정할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "신청 수정 성공"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> updateApplyById(
		@PathVariable Long applyId,
		@RequestBody ApplyUpdateDto requestBody
	) {
		ApplyReadDto updatedApply = applyService.updateApplyById(applyId, requestBody);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, updatedApply));
	}

	@DeleteMapping("/{applyId}")
	@Operation(summary = "신청 삭제 (ID 기반)", description = "집행부는 Apply ID를 사용하여 특정 신청을 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "신청 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<Void>> deleteApplyById(
		@PathVariable Long applyId
	) {
		applyService.deleteApplyById(applyId);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(ApiUtil.success(HttpStatus.NO_CONTENT));
	}
}
