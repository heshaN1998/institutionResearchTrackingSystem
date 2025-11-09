package lk.ijse.cmjd.reasearchtracker.project.service;
import lk.ijse.cmjd.reasearchtracker.common.exception.ResourceNotFoundException;
import lk.ijse.cmjd.reasearchtracker.common.exception.UnauthorizedException;
import lk.ijse.cmjd.reasearchtracker.project.dto.ProjectDTO;
import lk.ijse.cmjd.reasearchtracker.project.dto.ProjectStatusUpdateDTO;
import lk.ijse.cmjd.reasearchtracker.project.entity.Project;
import lk.ijse.cmjd.reasearchtracker.project.entity.Status;
import lk.ijse.cmjd.reasearchtracker.project.repository.ProjectRepository;
import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lk.ijse.cmjd.reasearchtracker.user.entity.UserRole;
import lk.ijse.cmjd.reasearchtracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectDTO createProject(ProjectDTO projectDTO, String currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Only ADMIN and PI can create projects
        if (currentUser.getRole() != UserRole.ADMIN && currentUser.getRole() != UserRole.PI) {
            throw new UnauthorizedException("Only ADMIN and PI can create projects");
        }

        // Validate PI ID is provided
        if (projectDTO.getPiId() == null || projectDTO.getPiId().isEmpty()) {
            throw new IllegalArgumentException("PI ID is required");
        }

        User pi = userRepository.findById(projectDTO.getPiId())
                .orElseThrow(() -> new ResourceNotFoundException("PI not found"));

        // Validate PI has appropriate role
        if (pi.getRole() != UserRole.PI && pi.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Assigned user must have PI or ADMIN role");
        }

        Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setSummary(projectDTO.getSummary());
        // Set default status if not provided
        project.setStatus(projectDTO.getStatus() != null ? projectDTO.getStatus() : Status.PLANNING);
        project.setPi(pi);
        project.setTags(projectDTO.getTags());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return convertToDTO(project);
    }

    public ProjectDTO updateProject(String id, ProjectDTO projectDTO, String currentUserId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Only ADMIN or project PI can update
        if (currentUser.getRole() != UserRole.ADMIN &&
                !project.getPi().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Not authorized to update this project");
        }


        if (projectDTO.getTitle() != null && !projectDTO.getTitle().isEmpty()) {
            project.setTitle(projectDTO.getTitle());
        }
        if (projectDTO.getSummary() != null) {
            project.setSummary(projectDTO.getSummary());
        }
        if (projectDTO.getStatus() != null) {
            project.setStatus(projectDTO.getStatus());
        }
        if (projectDTO.getTags() != null) {
            project.setTags(projectDTO.getTags());
        }
        if (projectDTO.getStartDate() != null) {
            project.setStartDate(projectDTO.getStartDate());
        }
        if (projectDTO.getEndDate() != null) {
            project.setEndDate(projectDTO.getEndDate());
        }

        if (projectDTO.getPiId() != null && !projectDTO.getPiId().isEmpty()) {
            User newPi = userRepository.findById(projectDTO.getPiId())
                    .orElseThrow(() -> new ResourceNotFoundException("PI not found"));

            // Validate new PI has appropriate role
            if (newPi.getRole() != UserRole.PI && newPi.getRole() != UserRole.ADMIN) {
                throw new IllegalArgumentException("Assigned user must have PI or ADMIN role");
            }

            project.setPi(newPi);
        }

        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    public void updateProjectStatus(String id, ProjectStatusUpdateDTO statusDTO, String currentUserId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //admin and Pi can
        if (currentUser.getRole() != UserRole.ADMIN &&
                !project.getPi().getId().equals(currentUserId)) {
            throw new UnauthorizedException("Not authorized to update project status");
        }


        if (statusDTO.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }

        project.setStatus(statusDTO.getStatus());
        projectRepository.save(project);
    }

    public void deleteProject(String id, String currentUserId) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //admin can delete
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("Only ADMIN can delete projects");
        }

        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setSummary(project.getSummary());
        dto.setStatus(project.getStatus());
        dto.setPiId(project.getPi().getId());
        dto.setPiName(project.getPi().getFullName());
        dto.setTags(project.getTags());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        return dto;
    }
}