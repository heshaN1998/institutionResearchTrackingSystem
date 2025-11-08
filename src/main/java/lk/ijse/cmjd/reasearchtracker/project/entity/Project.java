package lk.ijse.cmjd.reasearchtracker.project.entity;
import jakarta.persistence.*;
import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.ObjectInputFilter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Project {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ObjectInputFilter.Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_id", nullable = false)
    private User.user PI; // Principal Investigator

    private String tags;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }
}
