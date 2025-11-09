package lk.ijse.cmjd.reasearchtracker.document.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public class DocumentDTO {

        private String id;

        @NotBlank(message = "Project ID is required")
        private String projectId;

        @NotBlank(message = "Title is required")
        private String title;

        private String description;

        @NotBlank(message = "URL or path is required")
        private String urlOrPath;

        private String uploadedById;
        private String uploadedByName;
        private LocalDateTime uploadedAt;
    }

