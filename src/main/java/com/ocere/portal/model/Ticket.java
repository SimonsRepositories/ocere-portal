package com.ocere.portal.model;

import com.ocere.portal.enums.Priority;
import com.ocere.portal.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Entity
public class Ticket {
    // Default constructor is required by JPA
    public Ticket() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @NotNull(message = "status is compulsory")
    @Column(name = "status")
    private Status status;

    @NotNull(message = "subject is compulsory")
    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @NotNull(message = "It is compulsory to select an assignee")
    @Column(name = "assigned_user")
    private User assignedUser;

    @NotNull(message = "priority is compulsory")
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "attachments")
    private List<File> attachments;

    @NotNull(message = "It is compulsory to select an assigned group")
    @Column(name = "assigned_group")
    private Group assignedGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }


    public Group getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(Group assignedGroup) {
        this.assignedGroup = assignedGroup;
    }
}
