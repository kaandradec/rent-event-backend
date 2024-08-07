package com.rentevent.auth;

import com.rentevent.dto.request.ClientePassRequest;
import com.rentevent.dto.request.LoginRequest;
import com.rentevent.dto.request.RegisterRequest;
import com.rentevent.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://rent-event-frontend-msei.vercel.app")
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
    public ResponseEntity<?> registerClient(@RequestBody RegisterRequest request) {
        return authService.registerCliente(request)!=null?ResponseEntity.ok(authService.registerCliente(request)):ResponseEntity.notFound().build();
    }

}
