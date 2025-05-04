package org.billz.journalapp.scheduled_task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@Component
public class Scheduler {

    static final Logger logger= LoggerFactory.getLogger(Scheduler.class);
    private static final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedDelay = 5000)
    void executeFixedDelay(){
            logger.info("Fixed delay task executed....");

    }

    @Scheduled(fixedRate = 1000)
    void executefixedRate(){
        logger.info("fixed rate executed....");
    }


}
