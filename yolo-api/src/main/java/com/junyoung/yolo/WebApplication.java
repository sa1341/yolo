package com.junyoung.yolo;

import com.junyoung.yolo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class WebApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    private final MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("init!!");
      /*  Member member = Member.create("a79007714@gmail.com","임준영", 30);
        memberRepository.save(member);
        logger.info("Member saved: {}", member.getName());*/
    }
}
