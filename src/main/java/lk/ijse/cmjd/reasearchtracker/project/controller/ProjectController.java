package lk.ijse.cmjd.reasearchtracker.project.controller;
import jakarta.validation.Valid;
import lk.ijse.cmjd.reasearchtracker.common.response.ApiResponse;
import lk.ijse.cmjd.reasearchtracker.project.dto.ProjectDTO;
import lk.ijse.cmjd.reasearchtracker.project.dto.ProjectStatusUpdateDTO;
import lk.ijse.cmjd.reasearchtracker.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProjectController {

    private final ProjectService projectService;

   //getAllProjects
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    //create new project
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(
            @Valid @RequestBody ProjectDTO projectDTO,
            Authentication authentication) {
        String currentUserId = authentication.getName();
        ProjectDTO created = projectService.createProject(projectDTO, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable String id,
            @Valid @RequestBody ProjectDTO projectDTO,
            Authentication authentication) {
        String currentUserId = authentication.getName();
        ProjectDTO updated = projectService.updateProject(id, projectDTO, currentUserId);
        return ResponseEntity.ok(updated);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateProjectStatus(
            @PathVariable String id,
            @Valid @RequestBody ProjectStatusUpdateDTO statusDTO,
            Authentication authentication) {
        String currentUserId = authentication.getName();
        projectService.updateProjectStatus(id, statusDTO, currentUserId);
        return ResponseEntity.ok(new ApiResponse(true, "Project status updated successfully"));
    }

    //delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable String id,
            Authentication authentication) {
        String currentUserId = authentication.getName();
        projectService.deleteProject(id, currentUserId);
        return ResponseEntity.ok(new ApiResponse(true, "Project deleted successfully"));
    }


    @GetMapping("/pi/{piId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByPi(@PathVariable String piId) {
        List<ProjectDTO> projects = projectService.getProjectById(piId);
        return ResponseEntity.ok(projects);
    }


    @GetMapping("/my-projects")
    public ResponseEntity<List<ProjectDTO>> getMyProjects(Authentication authentication) {
        String currentUserId = authentication.getName();
        List<ProjectDTO> projects = projectService.getProjectById(currentUserId);
        return ResponseEntity.ok(projects);
    }
}