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

    private String companyName;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

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

    // private Group leadGroup;
    // private String contactName;
    // private String phone;
    // private String city;
    // private String country;
    // private String paymentTerms;
    // private String username;
    // private String tier;
    // private String satisfaction;
    // private String email;
    // private String website;
    // private String addressLine;
    // private String state;
    // private String postcode;
    // private String contactUsPage;
    // private String linkedIn;
    // Phones and Emails (List)
}
