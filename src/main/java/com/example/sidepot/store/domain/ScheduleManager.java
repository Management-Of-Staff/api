package com.example.sidepot.store.domain;

import com.example.sidepot.store.dto.TodoListCreateDto;
import com.example.sidepot.store.dto.TodoListCreateDto.ManagerCreateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Table(name = "schedule_manager")
@NoArgsConstructor
@Entity
public class ScheduleManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_mangaer_id")
    private Long scheduleManagerId;

    @NotNull
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;
    @Builder
    public ScheduleManager(Long staffId, String name, TodoList todoList){
        this.staffId = staffId;
        this.name = name;
        this.todoList = todoList;
    }

    public static ScheduleManager of(TodoList todoList, ManagerCreateDto managerCreateDto){
        return ScheduleManager.builder()
                .staffId(managerCreateDto.getStaffId())
                .name(managerCreateDto.getmanagerName())
                .todoList(todoList)
                .build();
    }

    public static List<ScheduleManager> ofList(TodoList todoList, List<ManagerCreateDto> managerCreateDtoList){
        return managerCreateDtoList.stream().map(managerCreateDto -> of(todoList, managerCreateDto)).collect(Collectors.toList());
    }

    public void update(TodoList todoList, ScheduleManager scheduleManager) {
        this.todoList = todoList;
        this.name = scheduleManager.getName();
    }
}
