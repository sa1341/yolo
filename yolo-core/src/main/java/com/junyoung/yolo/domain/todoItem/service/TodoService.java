package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.domain.LocalDateParser;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.junyoung.yolo.domain.todoItem.repository.TodoItemRepository;
import com.junyoung.yolo.domain.todoItem.repository.TodoJpaRepository;
import com.junyoung.yolo.exception.TodoItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.junyoung.yolo.domain.member.service.MemberServiceHelper.findExistingMember;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoItemRepository todoItemRepository;
    private final TodoJpaRepository todoJpaRepository;
    private final MemberRepository memberRepository;

    public TodoItemResponse saveTodoItem(TodoItemRequest todoItemRequest) {
        String memberId = todoItemRequest.getMemberId();
        Member findMember = findExistingMember(memberRepository, memberId);
        logger.info("memberId: {}", findMember.getId());
        TodoItem todoItem = todoItemRequest.toEntity();
        findMember.saveItem(todoItem);
        logger.info("saving TodoItem is success, id: [{}]", todoItem.getId());
        TodoItemResponse todoItemResponse = todoItem.changeTodoResponse();
        return todoItemResponse;
    }

    public TodoItemResponse fetchTodoItemById(String id) {
        Optional<TodoItem> opt = todoItemRepository.findById(id);
        TodoItem todoItem = opt.orElseThrow(() -> new TodoItemNotFoundException());
        TodoItemResponse todoItemResponse = todoItem.changeTodoResponse();
        return todoItemResponse;
    }

    @Transactional(readOnly = true)
    public List<TodoItemResponse> fetchTodoItems() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        List<TodoItemResponse> todoResponse = todoItems.stream()
                .map(TodoItem::changeTodoResponse)
                .collect(Collectors.toList());
        return todoResponse;
    }

    @Transactional(readOnly = true)
    public List<TodoItemResponse> fetchTodoItemsByDate(String memberId, String date) {
        String parsedDate = convertJsonToString(date);
        LocalDateParser localDateParser = new LocalDateParser(parsedDate);
        LocalDateTime startDate = localDateParser.startDate();
        LocalDateTime endDate = localDateParser.endDate();

        List<TodoItem> todoItems = todoJpaRepository.fetchTodoItemByDate(memberId, startDate, endDate);
        List<TodoItemResponse> todoResponse = todoItems.stream()
                .map(TodoItem::changeTodoResponse)
                .collect(Collectors.toList());

        return todoResponse;
    }

    private String convertJsonToString(String date) {
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> parseMap = jsonParser.parseMap(date);
        String parsedDate = (String) parseMap.get("date");
        return parsedDate;
    }

    public void deleteTodoItem(String id) {
       /* Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        TodoItem findTodoItem = todoItem.orElseThrow(() -> new TodoItemNotFoundException("TodoItem is not exist: " + id));*/
        TodoItem todoItem = todoJpaRepository.fetchTodoItemWithMember(id);

        if (todoItem != null) {
            logger.info("TodoItem text: {}", todoItem.getText());

            Member member = todoItem.getMember();
            logger.info("memberId: {}", member.getId());

            logger.warn("deleted TodoItemId: {}", id);
            member.deleteTodoItem(todoItem);
        }
    }

    public String changeTodoItem(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = todoItemRequest.toEntity();
        String id = todoItem.getId();
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isPresent()) {
            TodoItem findTodoItem = optionalTodoItem.get();
            findTodoItem.changeContent(todoItem.getText());
        }
        return String.valueOf(id);
    }
}
