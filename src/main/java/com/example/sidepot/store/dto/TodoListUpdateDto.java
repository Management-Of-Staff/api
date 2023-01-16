package com.example.sidepot.store.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoListUpdateDto {
    private Long todoListId;
    private Long storeId;
    private String todoListTitle;
    private List<ManagerUpdateDto> managerUpdateDtoList;
    private LocalDateTime taskStartTime;
    private List<TodoListDetailUpdateDto> todoListDetailUpdateDtoList;

    @Getter
    public static class ManagerUpdateDto{
        private Long ManagerId;
        private Long staffId;
        private String mangerName;
    }

    @Getter
    public static class TodoListDetailUpdateDto{
        private Long toodListDetailId;
        private String todoListDetail;
    }
}
