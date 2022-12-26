package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ApiOperation("테스트 컨트롤러")
    @GetMapping(Path.REST_BASE_PATH + "/test")
    public String test(){
        return "되냐?";
    }
}
