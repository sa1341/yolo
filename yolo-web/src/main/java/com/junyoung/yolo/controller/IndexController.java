package com.junyoung.yolo.controller;

import com.junyoung.yolo.domain.member.Member;
import com.junyoung.yolo.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final MemberService memberService;

    @GetMapping(value = "/index")
    public String index() {
        Member member = new Member();
        member.setName("junyoung");
        member.setAge(30);
        memberService.saveMember(member);
        return "Hello yoloman!";
    }
}
