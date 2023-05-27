package com.example.sidepot.command.store.dto;

import com.example.sidepot.command.store.domain.ScheduleManager;
import com.example.sidepot.command.store.domain.TodoList;
import com.example.sidepot.command.store.domain.TodoListDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TodoListResponseDto {

    private Long todoListId;
    private Long storeId;
    private String todoListTitle;
    private List<ManagerResponseDto> managerResponseDtoList;
    private LocalDateTime taskStartTime;
    private List<TodoListDetailResponseDto> todoListDetailResponseDtoList;

    @Builder
    public TodoListResponseDto(Long todoListId, Long storeId, String todoListTitle, List<ManagerResponseDto> managerResponseDtoList,
                               LocalDateTime taskStartTime, List<TodoListDetailResponseDto> todoListDetailResponseDtoList) {
        this.storeId = storeId;
        this.todoListId = todoListId;
        this.todoListTitle = todoListTitle;
        this.managerResponseDtoList = managerResponseDtoList;
        this.taskStartTime = taskStartTime;
        this.todoListDetailResponseDtoList = todoListDetailResponseDtoList;
    }

    public static TodoListResponseDto from(TodoList todoList){
        return TodoListResponseDto.builder()
                .todoListId(todoList.getTodoListId())
                .storeId(todoList.getStore().getStoreId())
                .todoListTitle(todoList.getTodoListTitle())
                .taskStartTime(todoList.getTaskStartTime())
                .managerResponseDtoList(ManagerResponseDto.fromList(todoList.getScheduleManagerList()))
                .todoListDetailResponseDtoList(TodoListDetailResponseDto.fromList(todoList.getTodoListDetailList()))
                .build();
    }

    public static List<TodoListResponseDto> fromList(List<TodoList> todoLists){
        return todoLists.stream().map(TodoListResponseDto::from).collect(Collectors.toList());
    }
    @Getter
    public static class ManagerResponseDto{
        private Long staffId;
        private String managerName;

        @Builder
        public ManagerResponseDto(Long staffId, String managerName){
            this.staffId = staffId;
            this.managerName = managerName;
        }

        public static ManagerResponseDto from(ScheduleManager scheduleManager){
            return ManagerResponseDto.builder()
                    .staffId(scheduleManager.getStaffId())
                    .managerName(scheduleManager.getName())
                    .build();
        }

        public static List<ManagerResponseDto> fromList(List<ScheduleManager> scheduleManagerList){
            return scheduleManagerList.stream().map(ManagerResponseDto::from).collect(Collectors.toList());
        }
    }

    @Getter
    public static class TodoListDetailResponseDto{
        private Long todoListDetailId;
        private String todoListDetail;

        @Builder
        public TodoListDetailResponseDto(Long todoListDetailId, String todoListDetail){
            this.todoListDetailId = todoListDetailId;
            this.todoListDetail = todoListDetail;
        }

        public static TodoListDetailResponseDto from(TodoListDetail todoListDetail){
            return TodoListDetailResponseDto.builder()
                    .todoListDetailId(todoListDetail.getTodoListDetailId())
                    .todoListDetail(todoListDetail.getTodoListDetail())
                    .build();
        }

        public static List<TodoListDetailResponseDto> fromList(List<TodoListDetail> todoListDetailList){
            return todoListDetailList.stream().map(TodoListDetailResponseDto::from).collect(Collectors.toList());
        }
    }


}
