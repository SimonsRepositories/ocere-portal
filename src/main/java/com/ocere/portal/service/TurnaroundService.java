package com.ocere.portal.service;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.Turnaround;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public interface TurnaroundService {

    List<Turnaround> findAll();

    Optional<Turnaround> findTurnaroundById(int id);

    Turnaround createTurnaround(Turnaround turnaround);

    void deleteTurnaroundById(int id) throws Exception;

    Turnaround updateTurnaround(Turnaround turnaround, int id) throws Exception;

    Timestamp getTurnaroundTimestamp(Ticket ticket);
}
