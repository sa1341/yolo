package com.junyoung.yolo;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.MemberRole;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@Slf4j
@EnableHystrix
@RequiredArgsConstructor
@SpringBootApplication
public class WebApplication implements CommandLineRunner {

    private final MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("init");
        final String email = "a79007714@gmail.com";
        initMember(email);
    }

    private void initMember(String email) {
        Member member = memberRepository.findById(email).orElse(Member.builder()
                .id(email)
                .name("임준영")
                .age(30)
                .role(MemberRole.ADMIN)
                .build());

        memberRepository.save(member);
    }
}
