package com.junyoung.yolo.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    private String id;
    private String name;
    private int age;

    @Builder
    public Member(String name, int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
    }

    public static Member create(String name, int age) {
        return Member.builder()
                     .name(name)
                     .age(age)
                     .build();
    }
}
