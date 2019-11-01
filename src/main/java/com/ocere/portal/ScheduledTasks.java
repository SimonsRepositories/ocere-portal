package com.ocere.portal;

import com.ocere.portal.enums.ClientStatus;
import com.ocere.portal.model.Client;
import com.ocere.portal.model.Job;
import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.Impl.MailService;
import com.ocere.portal.service.JobService;
import com.ocere.portal.tasks.NextJobTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class ScheduledTasks {
    private ClientService clientService;
    private JobService jobService;
    private TaskScheduler taskScheduler;
    private MailService mailService;

    @Autowired
    public ScheduledTasks(ClientService clientService, JobService jobService, TaskScheduler taskScheduler, MailService mailService) {
        this.clientService = clientService;
        this.jobService = jobService;
        this.taskScheduler = taskScheduler;
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void resetMonthlySpending() {
        for (Client client : clientService.findAll()) {
            client.setMonthlySpending(0.0);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkClientActivity() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date month = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -180);
        Date month2 = cal.getTime();

        for (Client client : clientService.findAll()) {
            Date mostRecent = new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime();
            // get most recent job
            for (Job job : client.getJobs()) {
                if (job.getEndDate().after(mostRecent)) {
                    mostRecent = job.getEndDate();
                }

                if (mostRecent.before(month)) {
                    client.setStatus(ClientStatus.ACTIVE);
                } else if (mostRecent.before(month2)) {
                    client.setStatus(ClientStatus.INACTIVE);
                } else {
                    client.setStatus(ClientStatus.DEAD);
                }
            }
        }
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void updateScheduledTasks() {
        for (Job job: jobService.findAll()) {
            NextJobTask nextJobTask = new NextJobTask(mailService, job.getOwner(), job);
            taskScheduler.schedule(nextJobTask, job.getEndDate());
        }
    }
}
