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

import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormCreateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormUpdateDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController("executiveApplyFormController")
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
public class ApplyFormController {
	private final ApplyFormService applyFormService;

	@PostMapping
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> createForm(
		@RequestBody ApplyFormCreateDto requestBody
	) {
		ApplyFormReadDto responseBody = applyFormService.createApplyForm(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

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

	@DeleteMapping("/{year}/{semester}")
	public ResponseEntity<ApiUtil.ApiSuccessResult<Void>> deleteForm(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		applyFormService.deleteApplyForm(year, semester);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(ApiUtil.success(HttpStatus.NO_CONTENT));
	}

	@GetMapping
	public ResponseEntity<ApiSuccessResult<List<ApplyFormReadDto>>> getAllForms() {
		List<ApplyFormReadDto> forms = applyFormService.getAllApplyForms();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, forms));
	}

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
