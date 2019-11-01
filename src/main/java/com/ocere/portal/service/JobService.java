package com.ocere.portal.service;

import com.ocere.portal.model.Job;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> findAll();

    void deleteJobById(int id) throws Exception;

    Optional<Job> findJobById(int id);

    Job createJob(Job note);

    Job updateJob(Job note, int id) throws Exception;

    Job saveJob(Job note);

    List<Job> findAllJobsByClientId(int id);

    List<Job> findAllByEndDateAfter(Date endDate);
}
