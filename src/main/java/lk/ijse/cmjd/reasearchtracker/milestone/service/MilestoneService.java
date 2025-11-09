package lk.ijse.cmjd.reasearchtracker.milestone.service;
import lk.ijse.cmjd.reasearchtracker.common.exception.ResourceNotFoundException;
import lk.ijse.cmjd.reasearchtracker.milestone.dto.MilestoneDTO;
import lk.ijse.cmjd.reasearchtracker.milestone.entity.Milestone;
import lk.ijse.cmjd.reasearchtracker.milestone.repository.MilestoneRepository;
import lk.ijse.cmjd.reasearchtracker.project.entity.Project;
import lk.ijse.cmjd.reasearchtracker.project.repository.ProjectRepository;
import lk.ijse.cmjd.reasearchtracker.user.entity.User;
import lk.ijse.cmjd.reasearchtracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<MilestoneDTO> getMilestonesByProject(String projectId) {
        return milestoneRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MilestoneDTO createMilestone(String projectId, MilestoneDTO milestoneDTO, String currentUserId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setTitle(milestoneDTO.getTitle());
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setDueDate(milestoneDTO.getDueDate());
        milestone.setIsCompleted(false);
        milestone.setCreatedBy(user);

        Milestone saved = milestoneRepository.save(milestone);
        return convertToDTO(saved);
    }

    public MilestoneDTO updateMilestone(String id, MilestoneDTO milestoneDTO) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));

        milestone.setTitle(milestoneDTO.getTitle());
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setDueDate(milestoneDTO.getDueDate());
        if (milestoneDTO.getIsCompleted() != null) {
            milestone.setIsCompleted(milestoneDTO.getIsCompleted());
        }

        Milestone updated = milestoneRepository.save(milestone);
        return convertToDTO(updated);
    }

    public void deleteMilestone(String id) {
        if (!milestoneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Milestone not found");
        }
        milestoneRepository.deleteById(id);
    }

    private MilestoneDTO convertToDTO(Milestone milestone) {
        MilestoneDTO dto = new MilestoneDTO();
        dto.setId(milestone.getId());
        dto.setProjectId(milestone.getProject().getId());
        dto.setTitle(milestone.getTitle());
        dto.setDescription(milestone.getDescription());
        dto.setDueDate(milestone.getDueDate());
        dto.setIsCompleted(milestone.getIsCompleted());
        dto.setCreatedById(milestone.getCreatedBy().getId());
        dto.setCreatedByName(milestone.getCreatedBy().getFullName());
        return dto;
    }
}
