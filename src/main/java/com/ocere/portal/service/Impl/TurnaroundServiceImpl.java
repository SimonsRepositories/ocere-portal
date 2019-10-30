package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.DynamicTurnaround;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.Turnaround;
import com.ocere.portal.repository.TurnaroundRepository;
import com.ocere.portal.service.TurnaroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TurnaroundServiceImpl implements TurnaroundService {
    private TurnaroundRepository turnaroundRepository;

    @Autowired
    public TurnaroundServiceImpl(TurnaroundRepository turnaroundRepository) {
        this.turnaroundRepository = turnaroundRepository;
    }

    public List<Turnaround> findAll() {
        return turnaroundRepository.findAll();
    }

    public Optional<Turnaround> findTurnaroundById(int id) {
        return turnaroundRepository.findById(id);
    }

    public Turnaround createTurnaround(Turnaround turnaround) {
        return turnaroundRepository.saveAndFlush(turnaround);
    }

    public void deleteTurnaroundById(int id) throws Exception {
        if (turnaroundRepository.existsById(id)) {
            turnaroundRepository.deleteById(id);
        } else {
            throw new Exception("Couldn't delete turnaround, because it didn't exist!");
        }
    }

    public Turnaround updateTurnaround(Turnaround turnaround, int id) throws Exception {
        Turnaround updatedTurnaround;
        Optional<Turnaround> optionalUpdatedTurnaround = findTurnaroundById(id);

        if (optionalUpdatedTurnaround.isPresent()) {
            updatedTurnaround = optionalUpdatedTurnaround.get();
            updatedTurnaround.setDescription(turnaround.getDescription());
            updatedTurnaround.setHours(turnaround.getHours());
        } else {
            throw new Exception("Couldn't update turnaround, because it didn't exist!");
        }

        return turnaroundRepository.saveAndFlush(updatedTurnaround);
    }

    @Override
    public Timestamp getTurnaroundTimestamp(Ticket ticket) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ticket.getCreatedAt().getTime());
        cal.add(Calendar.HOUR, ticket.getTurnaround().getHours());

        if (ticket.getDynamicTurnaround() == DynamicTurnaround.JOBEND && ticket.getJob() != null) {
            return new Timestamp(ticket.getJob().getEndDate().getTime());
        }
        if (ticket.getDynamicTurnaround() == DynamicTurnaround.CAMPAIGNSTART && ticket.getJob() != null) {
            return new Timestamp(ticket.getJob().getCampaignLaunchDate().getTime());
        }

        return new Timestamp(cal.getTime().getTime());
    }
}
