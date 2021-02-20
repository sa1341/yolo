package com.junyoung.yolo.domain.member.dto;

import com.junyoung.yolo.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class NewMemberReq {
    private String id;
    private String name;
    private int age;

    public Member toEntity() {
        return Member.create(id, name, age);
    }
}
