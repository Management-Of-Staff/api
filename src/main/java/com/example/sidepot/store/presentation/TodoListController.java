package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.domain.Auth;
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
    public ResponseEntity createTodoList(@ApiIgnore @AuthenticationPrincipal final Auth auth,
                                         @RequestBody TodoListCreateDto todoListCreateDto
                                         ){
        return ResponseEntity.ok(todoListService.createTodoList(todoListCreateDto));
    }
}
