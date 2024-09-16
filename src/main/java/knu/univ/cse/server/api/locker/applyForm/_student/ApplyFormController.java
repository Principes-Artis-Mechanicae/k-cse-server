package knu.univ.cse.server.api.locker.applyForm._student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT') and isAuthenticated()")
public class ApplyFormController {
}
