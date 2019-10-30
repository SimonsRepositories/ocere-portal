package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.repository.DefticketRepository;
import com.ocere.portal.repository.TemplateRepository;
import com.ocere.portal.service.DefticketService;
import com.ocere.portal.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefticketServiceImpl implements DefticketService {

    private DefticketRepository defticketRepository;

    @Autowired
    public DefticketServiceImpl(DefticketRepository defticketRepository) {
        this.defticketRepository = defticketRepository;
    }

    /*
        FIND
     */

    @Override
    public List<Ticket> findAllDeftickets() {
        return defticketRepository.findAllByDefticketTrue();
    }

    @Override
    public Ticket findDefticketById(int id) {
        return defticketRepository.findByIdAndDefticketTrue(id);
    }

    /*
        ACTIONS
     */

    @Override
    public void saveDefticket(Ticket defticket) {
        defticketRepository.saveAndFlush(defticket);
    }

    @Override
    public void removeDefticketById(int id) {
        defticketRepository.deleteById(id);
    }

    @Override
    public Ticket updateDefticket(Ticket defticket, int id) throws Exception {
        Ticket updatedDefticket;
        Optional<Ticket> optionalUpdatedDefticket = defticketRepository.findById(id);
        if (optionalUpdatedDefticket.isPresent()) {
            updatedDefticket = optionalUpdatedDefticket.get();
            updatedDefticket.setTemplate(defticket.isTemplate());
            updatedDefticket.setDefticket(defticket.isDefticket());
            updatedDefticket.setDefProducts(defticket.getDefProducts());
            updatedDefticket.setTurnaround(defticket.getTurnaround());
            updatedDefticket.setAssignedGroup(defticket.getAssignedGroup());
            updatedDefticket.setAssignedUser(defticket.getAssignedUser());
            updatedDefticket.setDescription(defticket.getDescription());
            updatedDefticket.setJob(defticket.getJob());
            updatedDefticket.setNotes(defticket.getNotes());
            updatedDefticket.setPriority(defticket.getPriority());
            updatedDefticket.setStatus(defticket.getStatus());
            updatedDefticket.setSubject(defticket.getSubject());
            updatedDefticket.setCreatedAt(defticket.getCreatedAt());
            updatedDefticket.setAuthor(defticket.getAuthor());
            updatedDefticket.setFiles(defticket.getFiles());
        } else {
            throw new Exception("Couldn’t update defticket, because it didn’t exist !");
        }
        return defticketRepository.saveAndFlush(updatedDefticket);
    }
}
