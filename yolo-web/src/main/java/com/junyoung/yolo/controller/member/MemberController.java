package com.junyoung.yolo.controller.member;

import com.junyoung.yolo.domain.member.dto.NewMemberReq;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody final NewMemberReq newMemberReq) {
        Member member = newMemberReq.toEntity();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchMemberWithTodoItems() {
        Member member = memberService.findById("a9a887d2-8f94-40d9-812e-c52594520f79");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
