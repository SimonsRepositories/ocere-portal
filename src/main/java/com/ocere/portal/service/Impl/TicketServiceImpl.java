package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.TicketRepository;
import com.ocere.portal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    public TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public boolean isTicketAlreadyPresent(Ticket ticket) {
        return ticketRepository.findById(ticket.getId()).isPresent();
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public void removeTicketById(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).get();
    }

    @Override
    public Ticket saveTicketById(Ticket ticket, int id) throws Exception {
        Ticket updatedTicket;
            Optional<Ticket> optionalUpdatedTicket = ticketRepository.findById(id);
            if (optionalUpdatedTicket.isPresent()) {
                updatedTicket = optionalUpdatedTicket.get();
                updatedTicket.setAssignedGroup(ticket.getAssignedGroup());
                updatedTicket.setAssignedUser(ticket.getAssignedUser());
                updatedTicket.setDescription(ticket.getDescription());
                updatedTicket.setId(ticket.getId());
                updatedTicket.setJob(ticket.getJob());
                updatedTicket.setNotes(ticket.getNotes());
                updatedTicket.setPriority(ticket.getPriority());
                updatedTicket.setStatus(ticket.getStatus());
                updatedTicket.setSubject(ticket.getSubject());

            } else {
                throw new Exception("Couldn’t update ticket, because it didn’t exist !");
            }
            return ticketRepository.saveAndFlush(updatedTicket);
    }

    @Override
    public List<Ticket> findAllByStatus(Status status) {
        return ticketRepository.findAllByStatus(status);
    }

    public List<Ticket> findAllByTurnaround() {
        List<Ticket> overdue = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Ticket ticket : findAll()) {
            if (ticket.getTurnaround().after(now)) {
                overdue.add(ticket);
            }
        }
        return overdue;
    }

    @Override
    public List<Ticket> findAllByAssignedUser(User user) {
        return ticketRepository.findAllByAssignedUser(user);
    }

    public List<Ticket> findAllByAssignedUserAndTurnaround(User user) {
        List<Ticket> overdue = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Ticket ticket : findAllByAssignedUser(user)) {
            if (ticket.getTurnaround().after(now)) {
                overdue.add(ticket);
            }
        }
        return overdue;
    }

    @Override
    public List<Ticket> findAllByAssignedUserAndStatus(User user, Status status) {
        return ticketRepository.findAllByAssignedUserAndStatus(user, status);
    }

    @Override
    public List<Ticket> findAllByAuthor(User user) {
        return ticketRepository.findAllByAuthor(user);
    }
}
