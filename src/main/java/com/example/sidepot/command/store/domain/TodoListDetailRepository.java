package com.example.sidepot.command.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListDetailRepository extends JpaRepository<TodoListDetail, Long> {
    TodoListDetail getTodoListDetailByTodoListDetailId(Long todoListDetailId);
}
