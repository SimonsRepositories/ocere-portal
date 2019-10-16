package com.ocere.portal.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> tickets;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private Set<DBFile> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
