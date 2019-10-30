package com.ocere.portal.repository;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByTemplateFalseAndDefticketFalse();

    Ticket findByIdAndTemplateFalseAndDefticketFalse(int id);

    List<Ticket> findAllByStatusAndTemplateFalseAndDefticketFalse(Status status);

    List<Ticket> findAllByAssignedUserAndTemplateFalseAndDefticketFalse(User user);

    List<Ticket> findAllByAssignedUserAndStatusAndTemplateFalseAndDefticketFalse(User user, Status status);

    List<Ticket> findAllByAuthorAndTemplateFalseAndDefticketFalse(User user);

    List<Ticket> findAllTicketsByJobId(int id);
}
