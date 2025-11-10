package lk.ijse.cmjd.reasearchtracker.auth.service;
import lk.ijse.cmjd.reasearchtracker.auth.dto.JwtAuthenticationResponse;
import lk.ijse.cmjd.reasearchtracker.auth.dto.LoginRequest;
import lk.ijse.cmjd.reasearchtracker.auth.dto.SignUpRequest;;
import lk.ijse.cmjd.reasearchtracker.config.JwtTokenProvider;
import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lk.ijse.cmjd.reasearchtracker.user.entity.UserRole;
import lk.ijse.cmjd.reasearchtracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lk.ijse.cmjd.reasearchtracker.config.JwtAuthenticationFilter

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

   //sign ups
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        log.info("Attempting to register new user: {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Username already exists: {}", request.getUsername());
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(UserRole.MEMBER); // Default role for new users

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {} with ID: {}",
                savedUser.getUsername(), savedUser.getId());

        // Authenticate NEW USER
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        savedUser.getId(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        log.debug("JWT token generated for user: {}", savedUser.getUsername());
        return new JwtAuthenticationResponse(
                jwt,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFullName(),
                savedUser.getRole().name()
        );
    }


    public JwtAuthenticationResponse login(LoginRequest request) {
        log.info("Login attempt for username: {}", request.getUsername());

        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", request.getUsername());
                    return new BadCredentialsException("Invalid username or password");
                });
        try {
            // Authenticate using user ID (not username)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = tokenProvider.generateToken(authentication);
            log.info("User logged in successfully: {}", user.getUsername());

            return new JwtAuthenticationResponse(
                    jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getRole().name()
            );

        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for user: {}", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());
    }
    //logout
    public void logout() {
        SecurityContextHolder.clearContext();
        log.info("User logged out successfully");
    }
}