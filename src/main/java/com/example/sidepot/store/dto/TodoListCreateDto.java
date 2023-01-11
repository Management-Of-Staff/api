package com.example.sidepot.store.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoListCreateDto {

    private Long storeId;
    private String todoListTitle;
    private List<ManagerCreateDto> managerCreateDtoList;
    private LocalDateTime taskStartTime;
    private List<TodoListDetailCreateDto> todoListDetailCreateDtoList;

    @Getter
    public static class ManagerCreateDto{
        private Long staffId;
        private String mangerName;
    }

    @Getter
    public static class TodoListDetailCreateDto{
        private String todoListDetail;
    }
}
