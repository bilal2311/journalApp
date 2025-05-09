package org.billz.journalapp.service;

import org.billz.journalapp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    UserScheduler userScheduler;

   // @Test
    public void testUserScheduler(){
        userScheduler.fetchUsersAndSendSaMail();
    }
}
