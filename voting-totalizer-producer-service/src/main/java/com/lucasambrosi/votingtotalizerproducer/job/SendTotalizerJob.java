package com.lucasambrosi.votingtotalizerproducer.job;

import com.lucasambrosi.votingtotalizerproducer.service.VotingTotalizerService;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendTotalizerJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendTotalizerJob.class);

    private VotingTotalizerService votingTotalizerService;

    public SendTotalizerJob(VotingTotalizerService votingTotalizerService) {
        this.votingTotalizerService = votingTotalizerService;
    }

    @Scheduled(cron = "${app.cron.send-totalizer}")
    @SchedulerLock(name = "sendTotalizerJob")
    public void sendTotalizerJob() {
        LOGGER.debug("Running 'sendTotalizerJob'.");
        votingTotalizerService.getTotalizersAndSend();
    }
}
