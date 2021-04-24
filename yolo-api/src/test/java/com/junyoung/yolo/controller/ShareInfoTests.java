package com.junyoung.yolo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShareInfoTests {

    @Test
    public void readFileByClassPath() throws Exception {
        //given
        ClassPathResource resource = new ClassPathResource("data/test.txt");
        System.out.println(resource.getFilename());
        System.out.println(resource.getURL());
        System.out.println(resource.getURI());
        System.out.println(resource.getPath());
        //when

        //then
     }
}
