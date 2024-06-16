package com.rentevent.auth;

import com.rentevent.dto.request.LoginRequest;
import com.rentevent.dto.request.RegisterRequest;
import com.rentevent.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
@CrossOrigin(origins = {"http://localhost:5173"})
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "user/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "user/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> loginClient(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginCliente(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> registerClient(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerCliente(request));
    }

}
