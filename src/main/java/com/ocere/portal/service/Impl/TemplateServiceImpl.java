package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.repository.TemplateRepository;
import com.ocere.portal.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateServiceImpl implements TemplateService {

    private TemplateRepository templateRepository;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /*
        FIND
     */

    @Override
    public List<Ticket> findAllTemplates() {
        return templateRepository.findAllByTemplateTrue();
    }

    @Override
    public Ticket findTemplateById(int id) {
        return templateRepository.findByIdAndTemplateTrue(id);
    }

    /*
        ACTIONS
     */

    @Override
    public void saveTemplate(Ticket template) {
        templateRepository.saveAndFlush(template);
    }

    @Override
    public void removeTemplateById(int id) {
        templateRepository.deleteById(id);
    }

    @Override
    public Ticket updateTemplate(Ticket template, int id) throws Exception {
        Ticket updatedTemplate;
        Optional<Ticket> optionalUpdatedTemplate = templateRepository.findById(id);
        if (optionalUpdatedTemplate.isPresent()) {
            updatedTemplate = optionalUpdatedTemplate.get();
            updatedTemplate.setTemplate(template.isTemplate());
            updatedTemplate.setDefticket(template.isDefticket());
            updatedTemplate.setTurnaround(template.getTurnaround());
            updatedTemplate.setAssignedGroup(template.getAssignedGroup());
            updatedTemplate.setAssignedUser(template.getAssignedUser());
            updatedTemplate.setDescription(template.getDescription());
            updatedTemplate.setJob(template.getJob());
            updatedTemplate.setNotes(template.getNotes());
            updatedTemplate.setPriority(template.getPriority());
            updatedTemplate.setStatus(template.getStatus());
            updatedTemplate.setSubject(template.getSubject());
            updatedTemplate.setCreatedAt(template.getCreatedAt());
            updatedTemplate.setAuthor(template.getAuthor());
            updatedTemplate.setFiles(template.getFiles());
        } else {
            throw new Exception("Couldn’t update template, because it didn’t exist !");
        }
        return templateRepository.saveAndFlush(updatedTemplate);
    }
}
