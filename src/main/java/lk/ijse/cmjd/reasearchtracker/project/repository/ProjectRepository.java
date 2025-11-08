package lk.ijse.cmjd.reasearchtracker.project.repository;
import lk.ijse.cmjd.reasearchtracker.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findByPiId(String piId);
}
