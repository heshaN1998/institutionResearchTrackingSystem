package lk.ijse.cmjd.reasearchtracker.user.dto;
import lk.ijse.cmjd.reasearchtracker.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private String id;
    private String username;
    private String fullName;
    private UserRole role;
    private LocalDateTime createdAt;
}
