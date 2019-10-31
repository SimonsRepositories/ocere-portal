package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.TicketRepository;
import com.ocere.portal.service.TicketService;
import com.ocere.portal.service.TurnaroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private TurnaroundService turnaroundService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TurnaroundService turnaroundService) {
        this.ticketRepository = ticketRepository;
        this.turnaroundService = turnaroundService;
    }

    /*
        FIND
     */

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAllByTemplateFalseAndDefticketFalse();
    }

    @Override
    public Ticket findTicketById(int id) {
        return ticketRepository.findByIdAndTemplateFalseAndDefticketFalse(id);
    }

    @Override
    public List<Ticket> findAllTicketsByStatus(Status status) {
        return ticketRepository.findAllByStatusAndTemplateFalseAndDefticketFalse(status);
    }

    @Override
    public List<Ticket> findAllTicketsByAssignedUser(User user) {
        return ticketRepository.findAllByAssignedUserAndTemplateFalseAndDefticketFalse(user);
    }

    @Override
    public List<Ticket> findAllTicketsByAssignedUserAndStatus(User user, Status status) {
        return ticketRepository.findAllByAssignedUserAndStatusAndTemplateFalseAndDefticketFalse(user, status);
    }

    @Override
    public List<Ticket> findAllTicketsByAuthor(User user) {
        return ticketRepository.findAllByAuthorAndTemplateFalseAndDefticketFalse(user);
    }

    @Override
    public List<Ticket> findAllTicketsByTurnaroundInFuture() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return findAllTickets().stream()
                .filter((ticket -> turnaroundService.getTurnaroundTimestamp(ticket).after(now)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findAllTicketsByAssignedUserAndTurnaroundInFuture(User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return findAllTicketsByAssignedUser(user).stream()
                .filter((ticket -> turnaroundService.getTurnaroundTimestamp(ticket).after(now)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findAllTicketsByJobId(int id) {
        return ticketRepository.findAllTicketsByJobId(id);
    }

    /*
        ACTIONS
     */

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.saveAndFlush(ticket);
    }

    @Override
    public void removeTicketById(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket updateTicket(Ticket ticket, int id) throws Exception {
        Ticket updatedTicket;
        Optional<Ticket> optionalUpdatedTicket = ticketRepository.findById(id);
        if (optionalUpdatedTicket.isPresent()) {
            updatedTicket = optionalUpdatedTicket.get();
            updatedTicket.setTemplate(ticket.isTemplate());
            updatedTicket.setDefticket(ticket.isDefticket());
            updatedTicket.setDefProducts(ticket.getDefProducts());
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
            updatedTicket.setDynamicTurnaround(ticket.getDynamicTurnaround());
        } else {
            throw new Exception("Couldn’t update ticket, because it didn’t exist !");
        }
        return ticketRepository.saveAndFlush(updatedTicket);
    }
}
