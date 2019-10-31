package com.ocere.portal.model;

import com.ocere.portal.enums.*;

import javax.persistence.*;
import java.util.Collections;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Ticket {

    /**
     * Creates a new ticket based on a predefined ticket, a job and an author
     * @param ticket
     * @param job
     * @param author
     */
    public Ticket(Ticket ticket, Job job, User author) {
        this.job = job;
        this.author = author;
        this.template = false;
        this.defticket = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.subject = ticket.getSubject();
        this.turnaround = ticket.getTurnaround();
        this.assignedUser = ticket.getAssignedUser();
        this.assignedGroup = ticket.getAssignedGroup();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority();
        this.status = ticket.getStatus();
        this.dynamicTurnaround = ticket.getDynamicTurnaround();
    }

    // Default constructor is required by JPA
    public Ticket() {
        this.assignedUser = new User();
        this.assignedGroup = new Usergroup();
        this.notes = Collections.emptySet();
        this.files = Collections.emptySet();
        this.status = Status.OPEN;
        this.priority = Priority.MEDIUM;
        this.turnaround = new Turnaround(4);
        this.template = false;
        this.defticket = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Usergroup assignedGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turnaround_id")
    private Turnaround turnaround;

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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "template")
    private boolean template;

    @Column(name = "defticket")
    private boolean defticket;

    @ElementCollection(targetClass = ProductType.class)
    @CollectionTable(name = "ticket_defproduct",
            joinColumns = @JoinColumn(name = "ticket_id"))
    @Column(name = "defproduct_id")
    private Set<ProductType> defProducts;

    @Enumerated
    private DynamicTurnaround dynamicTurnaround;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    public List<Note> getSortedNotes() {
        return notes.stream().sorted(Comparator.comparing(Note::getCreatedAt)).collect(Collectors.toList());
    }

    /*
        GETTER AND SETTER
    */

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

    public Turnaround getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(Turnaround turnaround) {
        this.turnaround = turnaround;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<DBFile> getFiles() {
        return files;
    }

    public void setFiles(Set<DBFile> files) {
        this.files = files;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isDefticket() {
        return defticket;
    }

    public void setDefticket(boolean defticket) {
        this.defticket = defticket;
    }

    public Set<ProductType> getDefProducts() {
        return defProducts;
    }

    public void setDefProducts(Set<ProductType> defProducts) {
        this.defProducts = defProducts;
    }

    public DynamicTurnaround getDynamicTurnaround() {
        return dynamicTurnaround;
    }

    public void setDynamicTurnaround(DynamicTurnaround dynamicTurnaround) {
        this.dynamicTurnaround = dynamicTurnaround;
    }
}
