package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.store.domain.*;
import com.example.sidepot.store.dto.TodoListCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final TodoListDetailRepository todoListDetailRepository;
    private final ScheduleManagerRepository scheduleManagerRepository;

    public ResponseDto createTodoList(TodoListCreateDto todoListCreateDto){
        TodoList todoList = todoListRepository.save(TodoList.builder()
                        .taskStartTime(todoListCreateDto.getTaskStartTime())
                        .storeId(todoListCreateDto.getStoreId())
                        .todoListTitle(todoListCreateDto.getTodoListTitle())
                        .build());

        todoListDetailRepository.saveAll(TodoListDetail.ofList(todoList, todoListCreateDto.getTodoListDetailCreateDtoList()));
        scheduleManagerRepository.saveAll(ScheduleManager.ofList(todoList, todoListCreateDto.getManagerCreateDtoList()));
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("POST")
                .message(String.format("해야할일 생성 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    public ResponseDto readTodoList(){
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("GET")
                .message(String.format("해야할일 조회 성공"))
                .statusCode(200)
                .data("")
                .build();
    }
}
