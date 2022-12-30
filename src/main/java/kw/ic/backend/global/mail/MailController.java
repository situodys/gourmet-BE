package kw.ic.backend.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MailController {

    private final MailService mailService;

    @PostMapping("/email")
    public ResponseEntity<String> sendAuthEmail(@RequestBody MailRequest mailRequest) throws Exception{
        String authCode = mailService.sendAuthCodeEmail(mailRequest.getEmail());

        return ResponseEntity.ok(authCode);
    }

    @PostMapping("/email/health")
    public ResponseEntity<Boolean> isAlreadySignupEmail(@RequestBody MailRequest mailRequest) throws Exception {
        return ResponseEntity.ok(mailService.isAlreadySignupEmail(mailRequest.getEmail()));
    }
}
