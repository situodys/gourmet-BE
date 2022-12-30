package kw.ic.backend.global.auth.controller;

import kw.ic.backend.global.auth.dto.AuthRequest;
import kw.ic.backend.global.auth.dto.Tokens;
import kw.ic.backend.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
