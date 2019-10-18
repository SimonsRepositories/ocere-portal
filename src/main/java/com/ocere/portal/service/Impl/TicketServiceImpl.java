package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.TicketRepository;
import com.ocere.portal.service.TicketService;
import com.ocere.portal.service.TurnaroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private TurnaroundService turnaroundService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TurnaroundService turnaroundService) {
        this.ticketRepository = ticketRepository;
        this.turnaroundService = turnaroundService;
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
                updatedTicket.setTemplate(ticket.isTemplate());
                updatedTicket.setTurnaround(ticket.getTurnaround());
                updatedTicket.setAssignedGroup(ticket.getAssignedGroup());
                updatedTicket.setAssignedUser(ticket.getAssignedUser());
                updatedTicket.setDescription(ticket.getDescription());
                updatedTicket.setJob(ticket.getJob());
                updatedTicket.setNotes(ticket.getNotes());
                updatedTicket.setPriority(ticket.getPriority());
                updatedTicket.setStatus(ticket.getStatus());
                updatedTicket.setSubject(ticket.getSubject());
                updatedTicket.setCreatedAt(ticket.getCreatedAt());
                updatedTicket.setAuthor(ticket.getAuthor());
                updatedTicket.setFiles(ticket.getFiles());
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
            if (turnaroundService.getTurnaroundTimestamp(ticket).after(now)) {
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
            if (turnaroundService.getTurnaroundTimestamp(ticket).after(now)) {
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

    @Override
    public Ticket findTemplateById(int id) {
        return ticketRepository.findByIdAndTemplateTrue(id);
    }

    @Override
    public List<Ticket> findAllTemplates() {
        return ticketRepository.findAllByTemplateTrue();
    }
}
