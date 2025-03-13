/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.EventDTO;
import ca.gbc.approvalservice.dto.UserDTO;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final RestTemplate restTemplate;

    public ApprovalService(ApprovalRepository approvalRepository, RestTemplate restTemplate) {
        this.approvalRepository = approvalRepository;
        this.restTemplate = restTemplate;
    }

    public Approval saveApproval(Approval approval) {
        return approvalRepository.save(approval);
    }

    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }

    public List<Approval> getPendingApprovals() {
        return approvalRepository.findByApproved(false); // Assuming false indicates pending approval
    }

    public Optional<Approval> getApprovalById(Long id) {
        return approvalRepository.findById(id);
    }

    public Approval updateApproval(Long id, Approval approvalDetails) {
        Approval approval = approvalRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Approval not found"));
        approval.setEventId(approvalDetails.getEventId());
        approval.setApproverId(approvalDetails.getApproverId());
        approval.setApproved(approvalDetails.isApproved());
        return approvalRepository.save(approval);
    }

    public void deleteApproval(Long id) {
        approvalRepository.deleteById(id);
    }

    public boolean hasApprovalPrivilege(String userId) {
        String url = "http://user-service:8088/api/users/" + userId;
        UserDTO user = restTemplate.getForObject(url, UserDTO.class);
        return user != null && user.getRole().equalsIgnoreCase("Approver");
    }

    public EventDTO fetchEventDetails(String eventId) {
        String url = "http://event-service:8089/api/events/" + eventId;
        return restTemplate.getForObject(url, EventDTO.class);
    }


}
