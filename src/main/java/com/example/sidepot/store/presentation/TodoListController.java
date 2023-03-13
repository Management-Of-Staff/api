package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.store.app.TodoListService;
import com.example.sidepot.store.dto.TodoListCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = "해야할일 관련 APIs")
@RequestMapping(Path.REST_BASE_PATH)
public class TodoListController {

    private final TodoListService todoListService;

    @PostMapping("/todoList")
    @ApiOperation(value = "[해야할일] 1. 해야할일 생성", notes = "해야할일을 생성하는 API")
    public ResponseEntity createTodoList(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                         @RequestParam Long storeId,
                                         @RequestBody TodoListCreateDto todoListCreateDto
                                         ){
        return ResponseEntity.ok(todoListService.createTodoList(storeId, todoListCreateDto));
    }

    @GetMapping("/todoList")
    @ApiOperation(value = "[해야할일] 2. 해야할일 모두 조회", notes = "해야할일을 모두 조회하는 API")
    public ResponseEntity readAllTodoLists(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                         @RequestParam Long storeId
    ){
        return ResponseEntity.ok(todoListService.readAllTodoList(storeId));
    }

    @GetMapping("/todoList/{todoListId}")
    @ApiOperation(value = "[해야할일] 3. 해야할일 상세 조회", notes = "해야할일을 상세 조회하는 API")
    public ResponseEntity findTodoList(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                           @PathVariable Long todoListId
    ){
        return ResponseEntity.ok(todoListService.findTodoListByTodoListId(todoListId));
    }
    @PostMapping("/todoList/{todoListId}")
    @ApiOperation(value = "[해야할일] 4. 해야할일 수정", notes = "해야할일을 수정하는 API")
    public ResponseEntity updateTodoList(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                         @PathVariable Long todoListId,
                                         @RequestParam Long storeId,
                                         @RequestBody TodoListCreateDto todoListUpdateDto
    ){
        return ResponseEntity.ok(todoListService.updateTodoList(todoListId, storeId, todoListUpdateDto));
    }

}
