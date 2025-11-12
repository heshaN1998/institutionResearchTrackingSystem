package lk.ijse.cmjd.reasearchtracker.auth.service;

import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lk.ijse.cmjd.reasearchtracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.debug("Loading user by ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new UsernameNotFoundException("User not found with id: " + userId);
                });

        log.debug("User loaded successfully: {}", user.getUsername());

        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user)
        );
    }
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsernameActual(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
        log.debug("User loaded successfully: {}", user.getUsername());
        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(),
                getAuthorities(user)
        );
    }
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }
}