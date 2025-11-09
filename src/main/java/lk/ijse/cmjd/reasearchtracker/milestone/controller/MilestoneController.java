package lk.ijse.cmjd.reasearchtracker.milestone.controller;
import jakarta.validation.Valid;
import lk.ijse.cmjd.reasearchtracker.common.response.ApiResponse;
import lk.ijse.cmjd.reasearchtracker.milestone.dto.MilestoneDTO;
import lk.ijse.cmjd.reasearchtracker.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/milestones")
@RequiredArgsConstructor
class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<MilestoneDTO>> getMilestonesByProject(@PathVariable String projectId) {
        return ResponseEntity.ok(milestoneService.getMilestonesByProject(projectId));
    }

    @PostMapping
    public ResponseEntity<MilestoneDTO> createMilestone(
            @PathVariable String projectId,
            @Valid @RequestBody MilestoneDTO milestoneDTO,
            Authentication authentication) {
        String currentUserId = authentication.getName();
        MilestoneDTO created = milestoneService.createMilestone(projectId, milestoneDTO, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
//seperate controller
@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
class MilestoneOperationsController {

    private final MilestoneService milestoneService;

    @PutMapping("/{id}")
    public ResponseEntity<MilestoneDTO> updateMilestone(
            @PathVariable String id,
            @Valid @RequestBody MilestoneDTO milestoneDTO) {
        return ResponseEntity.ok(milestoneService.updateMilestone(id, milestoneDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMilestone(@PathVariable String id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.ok(new ApiResponse(true, "Milestone deleted successfully"));
    }
}