package lk.ijse.cmjd.reasearchtracker.auth.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String userId;
    private String username;
    private String fullName;
    private String role;

    //constructor for response
    public JwtAuthenticationResponse(String accessToken, String userId,
                                     String username, String fullName, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }
}