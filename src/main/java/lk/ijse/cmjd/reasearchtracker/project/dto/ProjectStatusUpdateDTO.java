package lk.ijse.cmjd.reasearchtracker.project.dto;
import jakarta.validation.constraints.NotNull;
import lk.ijse.cmjd.reasearchtracker.project.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private Status status;
}