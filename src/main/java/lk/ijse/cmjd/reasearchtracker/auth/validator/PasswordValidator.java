package lk.ijse.cmjd.reasearchtracker.auth.validator;
import java.util.regex.Pattern;

public class PasswordValidator {

    // At least 8 characters, one uppercase, one lowercase, one number
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$"
    );

     //Validate password strength
    public static boolean isValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return password.length() >= 6; // Basic validation (can be enhanced)
    }

    //check it strong
    public static boolean isStrongPassword(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    public static String getStrengthMessage(String password) {
        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        if (password.length() < 8) {
            return "Weak password. Consider using at least 8 characters";
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return "Medium password. Add uppercase, lowercase, and numbers for stronger security";
        }
        return "Strong password";
    }
}