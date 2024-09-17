package knu.univ.cse.server.api.locker.apply._anonymous;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import knu.univ.cse.server.api.locker.apply.dto.ApplyCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReadDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportCreateDto;
import knu.univ.cse.server.api.locker.apply.dto.ApplyReportReadDto;
import knu.univ.cse.server.domain.service.locker.apply.ApplyService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController("AnonymousApplyController")
@RequestMapping("/application")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ApplyController {
	private final ApplyService applyService;

	@PostMapping("/primary")
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyPrimary(
		@RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handlePrimaryApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/additional")
	public ResponseEntity<ApiSuccessResult<ApplyReadDto>> applyAdditional(
		@RequestBody ApplyCreateDto requestBody
	) {
		ApplyReadDto responseBody = applyService.handleAdditionalApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}

	@PostMapping("/replacement")
	public ResponseEntity<ApiSuccessResult<ApplyReportReadDto>> applyReplacement(
		@RequestBody ApplyReportCreateDto requestBody
	) {
		ApplyReportReadDto responseBody = applyService.handleReplacementApply(requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiUtil.success(HttpStatus.CREATED, responseBody));
	}
}
