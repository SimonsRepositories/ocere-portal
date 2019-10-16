package com.ocere.portal.model;

import jdk.jfr.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Group {
    // Default constructor is required by JPA
    public Group() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "is_empty")
    private Boolean empty;

    @NotNull(message = "name is compulsory")
    private String name;

    @Timestamp
    @Column(name = "created_at")
    private Timestamp created_at;

    @Timestamp
    @Column(name = "updated_at")
    private Timestamp updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
