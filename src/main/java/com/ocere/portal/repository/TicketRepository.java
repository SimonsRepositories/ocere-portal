package com.ocere.portal.repository;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
