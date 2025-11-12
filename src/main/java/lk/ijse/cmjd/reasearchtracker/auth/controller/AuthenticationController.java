package lk.ijse.cmjd.reasearchtracker.auth.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd.reasearchtracker.auth.dto.JwtAuthenticationResponse;
import lk.ijse.cmjd.reasearchtracker.auth.dto.LoginRequest;
import lk.ijse.cmjd.reasearchtracker.auth.dto.SignUpRequest;
import lk.ijse.cmjd.reasearchtracker.auth.service.AuthenticationService;
import lk.ijse.cmjd.reasearchtracker.common.response.ApiResponse;
import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(
            @Valid @RequestBody SignUpRequest request) {
        log.info("Signup request received for username: {}", request.getUsername());
        JwtAuthenticationResponse response = authenticationService.signup(request);
        log.info("User registered successfully: {}", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request) {
        log.info("Login request received for username: {}", request.getUsername());
        JwtAuthenticationResponse response = authenticationService.login(request);
        log.info("User logged in successfully: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        log.debug("Getting current user details");
        User currentUser = authenticationService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse> checkAuthentication() {
        boolean isAuthenticated = authenticationService.isAuthenticated();
        ApiResponse response = new ApiResponse(
                isAuthenticated,
                isAuthenticated ? "User is authenticated" : "User is not authenticated"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        log.info("Logout request received");
        authenticationService.logout();
        return ResponseEntity.ok(
                new ApiResponse(true, "Logged out successfully")
        );
    }
}