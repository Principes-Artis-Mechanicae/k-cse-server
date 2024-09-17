package knu.univ.cse.server.api.locker.applyForm._anonymous;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController("AnonymousApplyFormController")
@RequestMapping("/forms")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ApplyFormController {

}
