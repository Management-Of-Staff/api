package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Optional<List<TodoList>> getAllByStoreId(Long StoreId);
    Optional<TodoList> getTodoListByTodoListId(Long todoListId);
}