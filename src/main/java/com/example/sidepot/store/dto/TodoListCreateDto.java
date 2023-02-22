package com.example.sidepot.store.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoListCreateDto {

    private String todoListTitle;
    private List<ManagerCreateDto> managerCreateDtoList;
    private LocalDateTime taskStartTime;
    private List<TodoListDetailCreateDto> todoListDetailCreateDtoList;

    @Getter
    public static class ManagerCreateDto{
        private Long managerId;
        private Long staffId;
        private String managerName;
    }

    @Getter
    public static class TodoListDetailCreateDto{
        private Long todoListDetailId;
        private String todoListDetail;
        private String completeCheck;
        private LocalDateTime completeTime;
    }
}
