package com.ocere.portal.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
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
    private String name;

    @NotNull(message = "Last name name is compulsory")
    @Column(name = "last_name")
    private String lastname;

    @NotNull(message = "email name is compulsory")
    @Email(message ="Email is invalid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "password name is compulsory")
    @Length(min=5, message = "Password should be at least 5 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
    private Set<Role> roles;

    @OneToMany(
            mappedBy = "assignedUser",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> tickets;
}
