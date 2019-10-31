package com.ocere.portal.service;

import com.ocere.portal.model.Ticket;

import java.util.List;

public interface DefticketService {
    void saveDefticket(Ticket defticket);
    Ticket updateDefticket(Ticket defticket, int id) throws Exception;
    void removeDefticketById(int id);

    List<Ticket> findAllDeftickets();
    Ticket findDefticketById(int id);
}
