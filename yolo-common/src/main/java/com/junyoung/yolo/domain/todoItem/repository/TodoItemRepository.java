package com.junyoung.yolo.domain.todoItem.repository;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
