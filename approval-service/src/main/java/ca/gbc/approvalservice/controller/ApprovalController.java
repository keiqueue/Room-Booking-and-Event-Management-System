/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.approvalservice.controller;

import ca.gbc.approvalservice.dto.EventDTO;
import ca.gbc.approvalservice.exception.EventNotFoundException;
import ca.gbc.approvalservice.exception.UserNotAuthorizedException;
import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {
    @Autowired
    private ApprovalService approvalService;

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNotAuthorized(UserNotAuthorizedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEventNotFound(EventNotFoundException ex) {
        return ex.getMessage();
    }

    @PostMapping
    public Approval createApproval(@RequestBody Approval approval) {
        if (!approvalService.hasApprovalPrivilege(approval.getApproverId())) {
            throw new RuntimeException("User is not authorized to approve this event.");
        }
        EventDTO event = approvalService.fetchEventDetails(approval.getEventId());
        if (event == null) {
            throw new RuntimeException("Event not found");
        }
        return approvalService.saveApproval(approval);
    }

    @GetMapping
    public List<Approval> getAllApprovals() {
        return approvalService.getAllApprovals();
    }


    @GetMapping("/pending")
    public List<Approval> getAllPendingApprovals() {
        return approvalService.getPendingApprovals();
    }

    @GetMapping("/{id}")
    public Approval getApprovalById(@PathVariable Long id) {
        return approvalService.getApprovalById(id).orElseThrow(() ->
                new RuntimeException("Approval not found"));
    }

    @PostMapping("/{id}")
    public Approval updateApproval(@PathVariable Long id, @RequestBody Approval approvalDetails) {
        return approvalService.updateApproval(id, approvalDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteApproval(@PathVariable Long id) {
        approvalService.deleteApproval(id);
    }
}
