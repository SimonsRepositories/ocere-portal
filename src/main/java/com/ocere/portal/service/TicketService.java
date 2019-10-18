package com.ocere.portal.service;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    void saveTicket(Ticket ticket);
    Ticket updateTicket(Ticket ticket, int id) throws Exception;
    void removeTicketById(int id);

    List<Ticket> findAllTickets();
    Ticket findTicketById(int id);
    List<Ticket> findAllTicketsByStatus(Status status);
    List<Ticket> findAllTicketsByAssignedUser(User user);
    List<Ticket> findAllTicketsByAssignedUserAndStatus(User user, Status status);
    List<Ticket> findAllTicketsByAuthor(User user);
    List<Ticket> findAllTicketsByTurnaroundInFuture();
    List<Ticket> findAllTicketsByAssignedUserAndTurnaroundInFuture(User user);
}
