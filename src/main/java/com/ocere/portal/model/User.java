package com.ocere.portal.model;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "auth_user")
public class User
{
    //Default constructor is required by JPA
    public User(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "auth_user_id")
    private int id;

    @NotNull(message = "first name is compulsory")
    @Column(name = "first_name")
    private String firstname;

    @NotNull(message = "last name is compulsory")
    @Column(name = "last_name")
    private String lastname;

    @NotNull(message = "email is compulsory")
    @Email(message ="Email is invalid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "password is compulsory")
    @Length(min=5, message = "Password should be at least 5 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", nullable = false))
    private Set<Role> roles;

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
}
