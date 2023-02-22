package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.store.domain.*;
import com.example.sidepot.store.dto.TodoListCreateDto;
import com.example.sidepot.store.dto.TodoListResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.example.sidepot.store.dto.TodoListCreateDto.*;

@RequiredArgsConstructor
@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final TodoListDetailRepository todoListDetailRepository;
    private final ScheduleManagerRepository scheduleManagerRepository;
    private final StoreRepository storeRepository;
    public ResponseDto createTodoList(Long storeId, TodoListCreateDto todoListCreateDto){

        Store store = storeRepository.getByStoreId(storeId)
                .orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_STORE));
        TodoList todoList = todoListRepository.save(TodoList.builder()
                        .taskStartTime(todoListCreateDto.getTaskStartTime())
                        .store(store)
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
    @Transactional(readOnly = true)
    public ResponseDto readAllTodoList(Long storeId){
        Store store = storeRepository.getByStoreId(storeId)
                .orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_STORE));
        List<TodoList> todoLists = todoListRepository.getAllByStore(store);
        todoLists = todoLists != null ? todoLists : Collections.emptyList();
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("GET")
                .message(String.format("해야할일 조회 성공"))
                .statusCode(200)
                .data(TodoListResponseDto.fromList(todoLists))
                .build();
    }
    @Transactional(readOnly = true)
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
    public ResponseDto updateTodoList(Long todoListId, Long storeId, TodoListCreateDto todoListUpdateDto){

        TodoList todoList = todoListRepository.getTodoListByTodoListId(todoListId)
                .orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_TODO_LIST));
        todoList.updateTodoList(todoList, todoListUpdateDto);
        todoListRepository.save(todoList);

        updateTodoListDetailList(todoList, todoListUpdateDto.getTodoListDetailCreateDtoList());
        updateScheduleManagerList(todoList, todoListUpdateDto.getManagerCreateDtoList());
        return ResponseDto.builder()
                .path(String.format("rest/v1/todoList"))
                .method("POST")
                .message(String.format("해야할일 수정 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    public void updateTodoListDetailList(TodoList todoList, List<TodoListDetailCreateDto> todoListDetailCreateDtoList){
        for(TodoListDetailCreateDto todoListDetailCreateDto : todoListDetailCreateDtoList){
            TodoListDetail todoListDetail = todoListDetailRepository.getTodoListDetailByTodoListDetailId
                    (todoListDetailCreateDto.getTodoListDetailId());
            if(todoListDetail != null) {
                todoListDetail.update(todoList, TodoListDetail.of(todoList, todoListDetailCreateDto));
                todoListDetailRepository.save(todoListDetail);
            }else {
                saveNewTodoListDetail(todoList, todoListDetailCreateDto);
            }
        }
    }

    public void updateScheduleManagerList(TodoList todoList, List<ManagerCreateDto> managerCreateDtoList){
        for(ManagerCreateDto managerCreateDto : managerCreateDtoList){
            ScheduleManager scheduleManager = scheduleManagerRepository.getScheduleManagerByScheduleManagerId(managerCreateDto.getManagerId());
            if(scheduleManager != null) {
                scheduleManager.update(todoList, ScheduleManager.of(todoList, managerCreateDto));
                scheduleManagerRepository.save(scheduleManager);
            }else {
                saveNewScheduleManager(todoList, managerCreateDto);
            }
        }
    }

    public void saveNewTodoListDetail(TodoList todoList, TodoListDetailCreateDto todoListDetailCreateDto){
        todoListDetailRepository.save(TodoListDetail.builder()
                .todoList(todoList)
                .todoListDetail(todoListDetailCreateDto.getTodoListDetail())
                .completeCheck(todoListDetailCreateDto.getCompleteCheck())
                .completeTime(todoListDetailCreateDto.getCompleteTime())
                .build());
    }
    public void saveNewScheduleManager(TodoList todoList, ManagerCreateDto managerCreateDto){
        scheduleManagerRepository.save(ScheduleManager.builder()
                .todoList(todoList)
                .name(managerCreateDto.getmanagerName())
                .staffId(managerCreateDto.getStaffId())
                .build());
    }
}
