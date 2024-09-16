package knu.univ.cse.server.api.applyForm;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import knu.univ.cse.server.domain.locker.dto.request.ApplyFormCreateDto;
import knu.univ.cse.server.domain.locker.dto.request.ApplyFormUpdateDto;
import knu.univ.cse.server.domain.locker.dto.response.ApplyFormReadDto;
import knu.univ.cse.server.domain.locker.service.ApplyFormService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class ApplyFormController {
	private final ApplyFormService applyFormService;

	@PostMapping
	@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> createForm(
		@RequestBody ApplyFormCreateDto requestBody
	) {
		ApplyFormReadDto responseBody = applyFormService.createApplyForm(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PutMapping("/{year}/{semester}")
	@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
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
	@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
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
	@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<ApplyFormReadDto>>> getAllForms() {
		List<ApplyFormReadDto> forms = applyFormService.getAllApplyForms();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, forms));
	}

	@GetMapping("/{year}/{semester}")
	@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> getFormByYearAndSemester(
		@PathVariable Integer year,
		@PathVariable Integer semester
	) {
		ApplyFormReadDto form = applyFormService.getApplyForm(year, semester);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, form));
	}
}
