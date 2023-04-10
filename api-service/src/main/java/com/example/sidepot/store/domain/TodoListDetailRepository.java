package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoListDetailRepository extends JpaRepository<TodoListDetail, Long> {
    TodoListDetail getTodoListDetailByTodoListDetailId(Long todoListDetailId);
}
