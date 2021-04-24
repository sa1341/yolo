package com.junyoung.yolo.domain.member.service;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.junyoung.yolo.domain.member.service.MemberServiceHelper.findExistingMember;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(String memberId) {
        Member findMember = findExistingMember(memberRepository, memberId);
        logger.info("memberId: {}", findMember.getName());
        List<TodoItem> todoItemList = findMember.getTodoItemList();
        todoItemList.forEach(todoItem -> {
            logger.info("text: {}", todoItem.getText());
        });
        return findMember;
    }
}

