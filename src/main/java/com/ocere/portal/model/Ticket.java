package com.ocere.portal.model;

import com.ocere.portal.enums.Priority;
import com.ocere.portal.enums.Status;

import javax.persistence.*;
import java.util.Collections;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Ticket {
    // Default constructor is required by JPA
    public Ticket() {
        this.assignedUser = new User();
        this.assignedGroup = new Usergroup();
        this.notes = Collections.emptySet();
        this.files = Collections.emptySet();
        this.status = Status.OPEN;
        this.priority = Priority.MEDIUM;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Usergroup assignedGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL
    )
    private Set<Note> notes;

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL
    )
    private Set<DBFile> files;

    @Column(name = "status")
    @Enumerated
    private Status status;

    @Column(name = "priority")
    @Enumerated
    private Priority priority;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @Column(name = "turnaround")
    private Timestamp turnaround;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Usergroup getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(Usergroup assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Timestamp getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(Timestamp turnaround) {
        this.turnaround = turnaround;
    }
}
