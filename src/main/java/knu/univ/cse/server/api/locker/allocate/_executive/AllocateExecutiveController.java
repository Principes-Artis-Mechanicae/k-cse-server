package knu.univ.cse.server.api.locker.allocate._executive;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.univ.cse.server.api.locker.allocate.dto.AllocateCreateDto;
import knu.univ.cse.server.api.locker.allocate.dto.AllocateRandomCreateDto;
import knu.univ.cse.server.api.locker.allocate.dto.AllocateReadDto;
import knu.univ.cse.server.domain.service.locker.allocate.AllocateService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
@Tag(name = "사물함 할당 (집행부)", description = "집행부용 사물함 할당 API")
public class AllocateExecutiveController {
	private final AllocateService allocateService;

	@PostMapping("/allocate")
	@Operation(summary = "사물함 할당 (지정)", description = "집행부는 특정 학생을 사물함에 할당할 수 있습니다. 사물함을 직접 지정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "사물함 할당 성공"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "사물함을 찾을 수 없음 (code: LOCKER_NOT_FOUND)"),
		@ApiResponse(responseCode = "409", description = "이미 할당이 존재함 (code: ALLOCATE_DUPLICATED)")
	})
	public ResponseEntity<ApiSuccessResult<AllocateReadDto>> allocateLocker(
		@RequestBody AllocateCreateDto requestBody
	) {
		AllocateReadDto responseBody = allocateService.allocateLockerByStudentNumber(
			requestBody.studentNumber(),
			requestBody.lockerName()
		);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/allocate/random")
	@Operation(summary = "사물함 할당 (랜덤)", description = "집행부는 특정 학생을 사물함에 할당할 수 있습니다. 사물함은 랜덤으로 지정됩니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "사물함 할당 성공"),
		@ApiResponse(responseCode = "404", description = "학생을 찾을 수 없음 (code: STUDENT_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "사용 가능한 사물함이 없음 (code: LOCKER_FULL_NOT_FOUND)"),
		@ApiResponse(responseCode = "409", description = "이미 할당이 존재함 (code: ALLOCATE_DUPLICATED)")
	})
	public ResponseEntity<ApiSuccessResult<AllocateReadDto>> allocateRandomLocker(
		@RequestBody AllocateRandomCreateDto requestBody
	) {
		AllocateReadDto responseBody = allocateService.allocateRandomLockerByStudentNumber(
			requestBody.studentNumber()
		);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/allocate/all")
	@Operation(summary = "모든 신청자 사물함 할당", description = "집행부는 현재 신청 폼의 모든 신청자에게 사물함을 할당할 수 있습니다. 사물함은 랜덤으로 지정됩니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "사물함 할당 성공"),
		@ApiResponse(responseCode = "404", description = "사용 가능한 사물함이 없음 (code: LOCKER_FULL_NOT_FOUND)"),
		@ApiResponse(responseCode = "404", description = "신청을 찾을 수 없음 (code: APPLY_NOT_FOUND)")
	})
	public ResponseEntity<ApiSuccessResult<List<AllocateReadDto>>> allocateAllLockers() {
		List<AllocateReadDto> responseBody = allocateService.allocateAllLockers();
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}
}
