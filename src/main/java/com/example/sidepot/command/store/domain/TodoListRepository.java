package com.example.sidepot.command.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> getAllByStore(Store store);
    Optional<TodoList> getTodoListByTodoListId(Long todoListId);
}
