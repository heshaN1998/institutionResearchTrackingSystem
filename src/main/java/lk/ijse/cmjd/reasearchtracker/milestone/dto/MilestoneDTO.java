package lk.ijse.cmjd.reasearchtracker.milestone.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MilestoneDTO {
    private String id;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private Boolean isCompleted;

    private String createdById;
    private String createdByName;
}