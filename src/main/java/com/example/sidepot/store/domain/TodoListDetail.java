package com.example.sidepot.store.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sidepot.store.dto.TodoListCreateDto.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "todo_list_detail")
public class TodoListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_detail_id")
    private Long todoListDetailId;

    @Column(name = "todo_list_detail")
    private String todoListDetail;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "complete_check")
    private String completeCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    @Builder
    public TodoListDetail(TodoList todoList, String todoListDetail){
        this.todoListDetail = todoListDetail;
        this.todoList = todoList;

    }

    public static TodoListDetail of(TodoList todoList, TodoListDetailCreateDto todoListDetailCreateDto){
        return TodoListDetail.builder()
                .todoListDetail(todoListDetailCreateDto.getTodoListDetail())
                .todoList(todoList)
                .build();
    }

    public static List<TodoListDetail> ofList(TodoList todoList, List<TodoListDetailCreateDto> todoListDetailCreateDtoList){
        return todoListDetailCreateDtoList.stream().map(todoListDetailCreateDto -> of(todoList, todoListDetailCreateDto)).collect(Collectors.toList());
    }

}