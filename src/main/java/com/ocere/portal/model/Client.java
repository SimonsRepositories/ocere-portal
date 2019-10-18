package com.ocere.portal.model;

import com.ocere.portal.enums.ClientStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private Set<Job> jobs;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private Set<Note> notes;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    // private String companyName;
    // private Usergroup leadGroup;
    // private String contactName;
    // private String phone;
    // private String street;
    // private String postcode
    // private String city
    // private String email;
    // private String website;

    // private String paymentTerms;
    // private String username;
    // private String tier;
    // private String satisfaction;
    // private String contactUsPage;
    // private String linkedIn;
    // Phones and Emails (List)


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }
}
