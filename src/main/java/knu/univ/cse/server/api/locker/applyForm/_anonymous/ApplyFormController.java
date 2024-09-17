package knu.univ.cse.server.api.locker.applyForm._anonymous;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.domain.service.locker.applyForm.ApplyFormService;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

@RestController("AnonymousApplyFormController")
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ApplyFormController {
	private final ApplyFormService applyFormService;

	@GetMapping("/now")
	public ResponseEntity<ApiSuccessResult<ApplyFormReadDto>> getNowForm() {
		ApplyFormReadDto responseBody = applyFormService.getNowApplyForm();
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiUtil.success(HttpStatus.OK, responseBody));
	}
}
