package com.junyoung.yolo.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() throws Exception {
        //given
        String password = "wnsdud2";
        String enPw = passwordEncoder.encode(password);

        //when
        System.out.println("enPw: " + enPw);
        boolean matchResult = passwordEncoder.matches(password, enPw);
        //then
        System.out.println("matchResult: " + matchResult);
     }
}
