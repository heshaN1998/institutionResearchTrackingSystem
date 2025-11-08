package lk.ijse.cmjd.reasearchtracker.milestone.repository;
import  lk.ijse.cmjd.reasearchtracker.milestone.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, String> {
    List<Milestone> findByProjectId(String projectId);
}
