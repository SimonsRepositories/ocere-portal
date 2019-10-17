package com.ocere.portal.repository;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByStatus(Status status);
    List<Ticket> findAllByTurnaround();
    List<Ticket> findAllByAssignedUser(User user);
    List<Ticket> findAllByAssignedUserAndTurnaround(User user);
    List<Ticket> findAllByAssignedUserAndStatus(User user, Status status);

}
