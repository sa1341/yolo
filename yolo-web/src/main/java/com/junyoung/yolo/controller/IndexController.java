package com.junyoung.yolo.controller;


import com.junyoung.yolo.domain.member.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //private final MemberService memberService;

    @GetMapping(value = "/index")
    public Member index() {
        Member member = Member.create("junyoung", 30);
        //memberService.saveMember(member);
        logger.debug("name: {}", member.getName());
        logger.debug("age: {}", member.getAge());
        return member;
    }
}
