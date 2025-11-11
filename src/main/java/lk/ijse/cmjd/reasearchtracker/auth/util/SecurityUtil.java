package lk.ijse.cmjd.reasearchtracker.auth.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;


public class SecurityUtil {

   //get current id
    public static Optional<String> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Optional.of(userDetails.getUsername());
        }
        return Optional.empty();
    }

    public static Optional<Authentication> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication);
    }

    /// check user has role
    public static boolean hasRole(String role) {
        return getCurrentAuthentication()
                .map(auth -> auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_" + role)))
                .orElse(false);
    }
    //if user is admin
    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }
    //if user is pi
    public static boolean isPI() {
        return hasRole("PI");
    }
    //check user is authenticate
    public static boolean isAuthenticated() {
        return getCurrentAuthentication()
                .map(auth -> auth.isAuthenticated() &&
                        !"anonymousUser".equals(auth.getPrincipal()))
                .orElse(false);
    }
}