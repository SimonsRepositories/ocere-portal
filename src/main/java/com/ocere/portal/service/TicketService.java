package com.ocere.portal.service;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;

import java.util.List;

public interface TicketService {
    void saveTicket(Ticket ticket);

    boolean isTicketAlreadyPresent(Ticket ticket);

    List<Ticket> findAll();

    void removeTicketById(int id);

    Ticket getTicketById(int id);

    Ticket saveTicketById(Ticket ticket, int id) throws Exception;

    List<Ticket> findAllByStatus(Status status);
    List<Ticket> findAllByAssignedUser(User user);
    List<Ticket> findAllByAssignedUserAndStatus(User user, Status status);
}
