package org.billz.journalapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {
    @Autowired
    UserRepositoryImpl userRepository;


    @Test
public void testSA(){
userRepository.getUserForSA();
}
}
