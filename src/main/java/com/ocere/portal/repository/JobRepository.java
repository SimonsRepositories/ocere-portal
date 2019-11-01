package com.ocere.portal.repository;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findAllByClient(Client client);
    List<Job> findAllByEndDateAfter(Date endDate);
}
