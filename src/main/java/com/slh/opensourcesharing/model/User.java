package com.slh.opensourcesharing.model;

import lombok.Getter;
import lombok.Setter;
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
    //Default constructor required by JPA
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
