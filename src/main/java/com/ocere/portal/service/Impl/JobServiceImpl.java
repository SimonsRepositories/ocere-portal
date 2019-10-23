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
            updatedJob.setCreatedAt(job.getCreatedAt());
            updatedJob.setAuthor(job.getAuthor());
            updatedJob.setTickets(job.getTickets());
            updatedJob.setProductTypes(job.getProductTypes());
            updatedJob.setValue(job.getValue());
            updatedJob.setCurrency(job.getCurrency());
            updatedJob.setStartDate(job.getStartDate());
            updatedJob.setEndDate(job.getEndDate());
            updatedJob.setDescription(job.getDescription());
            updatedJob.setWhiteLabel(job.isWhiteLabel());
            updatedJob.setCompany(job.getCompany());
            updatedJob.setWebsite(job.getWebsite());
            updatedJob.setStatus(job.getStatus());
            updatedJob.setFiles(job.getFiles());
            updatedJob.setTargetKeywords(job.getTargetKeywords());
            updatedJob.setReportingKeywords(job.getReportingKeywords());
            updatedJob.setSeoSearchEngines(job.getSeoSearchEngines());
            updatedJob.setOnPageReview(job.isOnPageReview());
            updatedJob.setHealthCheck(job.isHealthCheck());
            updatedJob.setLinkSearchEngines(job.getLinkSearchEngines());
            updatedJob.setGoogleDocLink(job.getGoogleDocLink());
            updatedJob.setExcelFile(job.getExcelFile());
            updatedJob.setCampaignTypes(job.getCampaignTypes());
            updatedJob.setCampaignLaunchDate(job.getCampaignLaunchDate());
            updatedJob.setSignOffRequired(job.isSignOffRequired());
            updatedJob.setFacebookId(job.getFacebookId());
            updatedJob.setFacebookUrl(job.getFacebookUrl());
            updatedJob.setSetUpRequired(job.isSetUpRequired());
            updatedJob.setGoogleAdsId(job.getGoogleAdsId());
            updatedJob.setMonthlyClickSpend(job.getMonthlyClickSpend());
            updatedJob.setTargetAreas(job.getTargetAreas());
            updatedJob.setLandingPageUrls(job.getLandingPageUrls());
            updatedJob.setAdwordsMainGoal(job.getAdwordsMainGoal());
            updatedJob.setFacebookMainGoal(job.getFacebookMainGoal());
            updatedJob.setThankYouPageUrl(job.getThankYouPageUrl());
            updatedJob.setReportingEmail(job.getReportingEmail());
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
