package com.ocere.portal.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Turnaround {

    public Turnaround() { }

    public Turnaround(int initialId) {
        this.id = initialId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false, unique = true)
    private int hours;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "turnaround"
    )
    private Set<Ticket> tickets;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
