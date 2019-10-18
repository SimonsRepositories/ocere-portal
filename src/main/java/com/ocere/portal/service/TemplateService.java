package com.ocere.portal.service;

import com.ocere.portal.model.Ticket;

import java.util.List;

public interface TemplateService {
    void saveTemplate(Ticket template);
    Ticket updateTemplate(Ticket template, int id) throws Exception;
    void removeTemplateById(int id);

    List<Ticket> findAllTemplates();
    Ticket findTemplateById(int id);
}
