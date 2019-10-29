package com.ocere.portal.model;

import com.ocere.portal.enums.ProductType;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class PredefinedTicketCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Enumerated
    @Column(nullable = false, unique = true)
    private ProductType productType;

    @OneToMany(
            mappedBy = "predefinedTicketCollection",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> ticketSet;

    public List<Ticket> getSortedTickets() {
        return this.ticketSet.stream().sorted(Comparator.comparing(Ticket::getCreatedAt)).collect(Collectors.toList());
    }

    /*

     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }
}
