package com.example.sidepot.store.domain;

import lombok.Builder;
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

    @Column(name = "todo_list_title")
    private String todoListTitle;

    @OneToMany(mappedBy = "todoList")
    private List<TodoListDetail> todoListDetailList = new ArrayList<>();

    @Column(name = "task_start_time")
    private LocalDateTime taskStartTime;

    @OneToMany(mappedBy = "todoList")
    private List<ScheduleManager> scheduleManagerList = new ArrayList<>();

    @Column(name = "store_id")
    private Long storeId;

    @Builder
    public TodoList(Long storeId, List<TodoListDetail> todoListDetailList, LocalDateTime taskStartTime, List<ScheduleManager> scheduleManagerList, String todoListTitle) {
        this.storeId = storeId;
        this.todoListDetailList = todoListDetailList;
        this.scheduleManagerList = scheduleManagerList;
        this.taskStartTime = taskStartTime;
        this.todoListTitle = todoListTitle;
    }

}