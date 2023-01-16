package com.example.sidepot.store.domain;

import com.example.sidepot.store.dto.TodoListCreateDto;
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
    @Column(name = "todo_list_detail_id")
    private List<TodoListDetail> todoListDetailList = new ArrayList<>();

    @Column(name = "task_start_time")
    private LocalDateTime taskStartTime;

    @OneToMany(mappedBy = "todoList")
    @Column(name = "schedule_manager_id")
    private List<ScheduleManager> scheduleManagerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public TodoList(Store store, List<TodoListDetail> todoListDetailList, LocalDateTime taskStartTime, List<ScheduleManager> scheduleManagerList, String todoListTitle) {
        this.store = store;
        this.todoListDetailList = todoListDetailList;
        this.scheduleManagerList = scheduleManagerList;
        this.taskStartTime = taskStartTime;
        this.todoListTitle = todoListTitle;
    }

    public void updateTodoListDetail(List<TodoListDetail> todoListDetailList){
        this.todoListDetailList.clear();
        this.todoListDetailList.addAll(todoListDetailList);
        for( TodoListDetail todoListDetail : todoListDetailList) {
            todoListDetail.update(this, todoListDetail);
        }
    }

    public void updateScheduleManager(List<ScheduleManager> scheduleManagerList){
        this.scheduleManagerList.clear();
        this.scheduleManagerList.addAll(scheduleManagerList);
        for( ScheduleManager scheduleManager : scheduleManagerList) {
            scheduleManager.update(this, scheduleManager);
        }
    }

    public void updateTodoList(TodoList todoList, TodoListCreateDto todoListUpdateDto){
        if(todoListUpdateDto.getTodoListTitle() != null) this.todoListTitle = todoListUpdateDto.getTodoListTitle();
        if(todoListUpdateDto.getTaskStartTime() != null) this.taskStartTime = todoListUpdateDto.getTaskStartTime();

        if(todoListUpdateDto.getTodoListDetailCreateDtoList() != null && !todoListUpdateDto.getTodoListDetailCreateDtoList().isEmpty()){
            updateTodoListDetail( TodoListDetail.ofList(todoList, todoListUpdateDto.getTodoListDetailCreateDtoList()));
        }

        if(todoListUpdateDto.getManagerCreateDtoList() != null && !todoListUpdateDto.getManagerCreateDtoList().isEmpty()){
            updateScheduleManager(ScheduleManager.ofList(todoList, todoListUpdateDto.getManagerCreateDtoList()));
        }
    }



}