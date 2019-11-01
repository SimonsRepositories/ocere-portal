package com.ocere.portal.tasks;

import com.ocere.portal.model.Job;
import com.ocere.portal.model.User;
import com.ocere.portal.service.Impl.MailService;

public class NextJobTask implements Runnable{

    private MailService mailService;
    private User recipient;
    private Job job;

    public NextJobTask(MailService mailService,
                       User recipient,
                       Job job){
        this.mailService = mailService;
        this.recipient = recipient;
        this.job = job;
    }

    @Override
    public void run() {
        try {
            mailService.sendMail("oliver.dietsche@ocere.com", "rlbszovxmhlwjkvy", recipient.getEmail(),
                    job.getCompany() + " is about to set up\n",
                            "Click here to approve the new job http://localhost:8080/jobs/clone?jobId=" + job.getId() + "\n");
        } catch (Exception e) {
            System.out.println("mail sending isn't possible with your email and mailpassword");
        }
    }
}
