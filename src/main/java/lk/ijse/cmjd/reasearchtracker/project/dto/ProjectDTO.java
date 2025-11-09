package lk.ijse.cmjd.reasearchtracker.project.dto;
import jakarta.transaction.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.cmjd.reasearchtracker.project.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectDTO {
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    private String summary;

    @NotNull(message = "Status is required")
    private Status status;

    @NotBlank(message = "PI ID is required")
    private String piId;

    private String piName;

    private String tags;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}