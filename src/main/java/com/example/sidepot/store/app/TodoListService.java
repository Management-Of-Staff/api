package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.store.domain.*;
import com.example.sidepot.store.dto.TodoListCreateDto;
import com.example.sidepot.store.dto.TodoListResponseDto;
import com.example.sidepot.store.dto.TodoListUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public ResponseDto readAllTodoList(Long storeId){
        List<TodoList> todoLists = todoListRepository.getAllByStoreId(storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_TODO_LIST));
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("GET")
                .message(String.format("해야할일 조회 성공"))
                .statusCode(200)
                .data(TodoListResponseDto.fromList(todoLists))
                .build();
    }

    public ResponseDto findTodoListByTodoListId(Long todoListId){
        TodoList todoList = todoListRepository.getTodoListByTodoListId(todoListId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_TODO_LIST));
        TodoListResponseDto todoListResponseDto = TodoListResponseDto.from(todoList);
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("GET")
                .message(String.format("해야할일 상세 조회 성공"))
                .statusCode(200)
                .data(todoListResponseDto)
                .build();
    }

    @Transactional
    public ResponseDto updateTodoList(TodoListUpdateDto todoListUpdateDto){

        TodoList todoList = todoListRepository.getTodoListByTodoListId(todoListUpdateDto.getTodoListId())
                .orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_TODO_LIST));
        todoList.updateTodoList(todoList, todoListUpdateDto);
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("POST")
                .message(String.format("해야할일 수정 성공"))
                .statusCode(200)
                .data(todoList)
                .build();
    }
}
