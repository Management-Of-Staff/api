package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "todo_list")
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<TodoListDetail> todoListDetail = new ArrayList<>();

    private LocalDateTime taskStartTime;

    @OneToMany
    private List<Manager> manager = new ArrayList<>();

    private LocalDateTime completeTime;

    private String completeCheck;
}