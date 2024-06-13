package com.rentevent.controller;

import com.rentevent.model.cliente.UserDTO;
import com.rentevent.service.client.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class UserConfiguration {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("UserDetails: " + userDetails);

            UserDTO userDTO = userService.getUserByUsername(userDetails.getUsername());
            return ResponseEntity.ok(userDTO);
        } else {
            System.err.println("User is not authenticated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

