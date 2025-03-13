package ca.gbc.approvalservice;

import ca.gbc.approvalservice.model.Approval;
import ca.gbc.approvalservice.repository.ApprovalRepository;
import ca.gbc.approvalservice.service.ApprovalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ApprovalServiceTest {

    @InjectMocks
    private ApprovalService approvalService;

    @Mock
    private ApprovalRepository approvalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveApproval() {
        Approval approval = new Approval();
        approval.setEventId("event123");
        approval.setApproverId("approver123");
        approval.setApproved(true);

        when(approvalRepository.save(any(Approval.class))).thenReturn(approval);

        Approval savedApproval = approvalService.saveApproval(approval);
        assertNotNull(savedApproval);
        assertEquals("event123", savedApproval.getEventId());
        assertEquals("approver123", savedApproval.getApproverId());
    }

    @Test
    void shouldGetApprovalById() {
        Approval approval = new Approval();
        approval.setEventId("event123");
        approval.setApproverId("approver123");

        when(approvalRepository.findById(1L)).thenReturn(Optional.of(approval));

        Optional<Approval> foundApproval = approvalService.getApprovalById(1L);
        assertNotNull(foundApproval);
        assertEquals("event123", foundApproval.get().getEventId());
    }
}
