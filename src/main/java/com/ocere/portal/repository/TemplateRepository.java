package com.ocere.portal.repository;

import com.ocere.portal.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByTemplateTrue();

    Ticket findByIdAndTemplateTrue(int id);
}
