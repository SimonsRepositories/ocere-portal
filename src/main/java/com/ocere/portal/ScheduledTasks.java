package com.ocere.portal;

import com.ocere.portal.model.Client;
import com.ocere.portal.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private ClientService clientService;

    @Autowired
    public ScheduledTasks(ClientService clientService) {
        this.clientService = clientService;
    }

    @Scheduled(cron = "00 00 1 * * *")
    public void scheduleTaskWithCronExpression() {
        for (Client client : clientService.findAll()) {
            client.setMonthlySpending(0.0);
        }

        /*
        // get exchange rates for GBP to every currency
        CurrencyConversion conversionGBP = MonetaryConversions.getConversion("GBP");

        for (Client client : clientService.findAll()) {
            // add monthly spending of previous month to totalspending
            client.setTotalSpending(client.getTotalSpending() + client.getMonthlySpending());

            // set new monthly spending to 0
            client.setMonthlySpending(0.0);

            // set new monthly spending according to current jobs
            double costInGBP;
            for (Job job : client.getJobs()) {
                costInGBP = 0.0;
                switch (job.getCurrency()) {
                    case EUR:
                        // get value of job in monetary amount and converse back to double
                        costInGBP = Monetary.getDefaultAmountFactory().setNumber(job.getTotalValue()).setCurrency("EUR").create().with(conversionGBP).getNumber().doubleValue();

                        client.setMonthlySpending(client.getMonthlySpending() + costInGBP);
                        break;

                    case CAD:
                        // get value of job in monetary amount and converse back to double
                        costInGBP = Monetary.getDefaultAmountFactory().setNumber(job.getTotalValue()).setCurrency("CAD").create().with(conversionGBP).getNumber().doubleValue();

                        client.setMonthlySpending(client.getMonthlySpending() + costInGBP);
                        break;

                    case USD:
                        // get value of job in monetary amount and converse back to double
                        costInGBP = Monetary.getDefaultAmountFactory().setNumber(job.getTotalValue()).setCurrency("USD").create().with(conversionGBP).getNumber().doubleValue();

                        client.setMonthlySpending(client.getMonthlySpending() + costInGBP);
                        break;

                    case GBP:
                        // get value of job in monetary amount and converse back to double
                        costInGBP = Monetary.getDefaultAmountFactory().setNumber(job.getTotalValue()).setCurrency("GBP").create().getNumber().doubleValue();

                        client.setMonthlySpending(client.getMonthlySpending() + costInGBP);
                        break;

                    default:
                        // unknown currency
                        logger.error("Currency " + job.getCurrency() + " is not supported");
                }
            }
        }*/

    }

}
