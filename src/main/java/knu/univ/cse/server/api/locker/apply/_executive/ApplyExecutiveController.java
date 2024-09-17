package knu.univ.cse.server.api.locker.apply._executive;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
@Tag(name = "Executive Apply", description = "집행부가 사용하는 신청 API 입니다.")
public class ApplyExecutiveController {

}
