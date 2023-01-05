package com.example.sidepot.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Table(name = "todo_list")
@NoArgsConstructor
@Entity
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private Long todoListId;

    @OneToMany
    @Column(name = "todo_list_detail")
    private List<TodoListDetail> todoListDetail = new ArrayList<>();

    @Column(name = "task_start_time")
    private LocalDateTime taskStartTime;

    @OneToMany
    @Column(name = "manager")
    private List<ScheduleManager> manager = new ArrayList<>();

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "complete_check")
    private String completeCheck;
}