package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Job;
import com.ocere.portal.repository.JobRepository;
import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private ClientService clientService;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, ClientService clientService) {
        this.jobRepository = jobRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Optional<Job> findJobById(int id) {
        return jobRepository.findById(id);
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.saveAndFlush(job);
    }

    @Override
    public void deleteJobById(int id) throws Exception {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        } else {
            throw new Exception("Couldn't delete job, because it didn't exist!");
        }
    }

    @Override
    public Job updateJob(Job job, int id) throws Exception {
        Job updatedJob;
        Optional<Job> optionalUpdatedJob = findJobById(id);

        if (optionalUpdatedJob.isPresent()) {
            updatedJob = optionalUpdatedJob.get();
            updatedJob.setClient(job.getClient());
            updatedJob.setTickets(job.getTickets());
            updatedJob.setCreatedAt(job.getCreatedAt());
            updatedJob.setAuthor(job.getAuthor());
        } else {
            throw new Exception("Couldn't update job, because it didn't exist!");
        }

        return jobRepository.saveAndFlush(updatedJob);
    }

    @Override
    public void saveJob(Job job) {
        jobRepository.saveAndFlush(job);
    }

    @Override
    public List<Job> findAllJobsByClientId(int id) {
        return jobRepository.findAllByClient(clientService.findClientById(id));
    }
}
