package com.ocere.portal.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "auth_user")
public class User
{
    //Default constructor is required by JPA
    public User(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id field must not be empty")
    @Column(name= "auth_user_id")
    private int id;

    @NotEmpty(message = "First name field must not be empty")
    @Column(name = "first_name")
    private String firstname;

    @NotEmpty(message = "Last name field must not be empty")
    @Column(name = "last_name")
    private String lastname;

    @NotEmpty(message = "Email field must not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password field must not be empty")
    @Length(min=8, message = "Password should be at least 8 characters")
    //Patttern: requires one lower case, one upper case, one digit and no spaces
    @Pattern(message="requires one lower case, one upper case, one digit and no spaces", regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,}$")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Your mail password field must not be empty.")
    @Column(name = "mailpassword")
    private String mailpassword;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", nullable = false))
    private Set<Role> roles;

    private Boolean client;

    @OneToMany(
            mappedBy = "assignedUser",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> tickets;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> createdTickets;

    public Boolean getClient() {
        return client;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Set<Note> createdNotes;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private Set<Job> job;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Set<DBFile> dbFile;

    public String getFullName() { return firstname + " " + lastname; }

    public String getMailpassword() {
        return mailpassword;
    }

    public void setMailpassword(String mailpassword) {
        this.mailpassword = mailpassword;
    }

    public String getName() {
        return firstname + " " + lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Ticket> getCreatedTickets() {
        return createdTickets;
    }

    public void setCreatedTickets(Set<Ticket> createdTickets) {
        this.createdTickets = createdTickets;
    }

    public Set<Note> getCreatedNotes() {
        return createdNotes;
    }

    public void setCreatedNotes(Set<Note> createdNotes) {
        this.createdNotes = createdNotes;
    }

    public Set<Job> getJob() {
        return job;
    }

    public void setJob(Set<Job> job) {
        this.job = job;
    }
}
