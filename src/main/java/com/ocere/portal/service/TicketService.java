package com.ocere.portal.service;

import com.ocere.portal.model.Ticket;

import java.util.List;

public interface TicketService {
    void saveTicket(Ticket ticket);

    boolean isTicketAlreadyPresent(Ticket ticket);

    List<Ticket> findAll();

    void removeTicketById(int id);

    Ticket getTicketById(int id);

    Ticket saveTicketById(Ticket ticket, int id) throws Exception;
}
