/**
 * COMP 3095 - Group Assignment
 * @Auhtors: Jam Furaque, Andrew Stewart, Kei Ishikawa, Carl Trinidad
 * */
package ca.gbc.approvalservice.dto;

public class ApprovalDTO {
    private String id;
    private String eventId;
    private String approverId;
    private boolean approved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
