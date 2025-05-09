package org.billz.journalapp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest

public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    //@Disabled("Learning more about it")
    //@Test
    public void sendEmail(){
        emailService.sendEmail("bilal.abbasi2311@gmail.com","Hello Abbasi","How are you ?");
    }

}
