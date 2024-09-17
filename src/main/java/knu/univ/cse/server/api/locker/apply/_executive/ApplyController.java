package knu.univ.cse.server.api.locker.apply._executive;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController("executiveApplyController")
@RequestMapping("/apply")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EXECUTIVE') and isAuthenticated()")
public class ApplyController {

}
