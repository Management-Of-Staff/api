package com.example.sidepot.global.security;


import org.springframework.web.bind.annotation.GetMapping;

public class FilterExceptionController {

    @GetMapping("/accessDenied")
    public void accessDeniedException(){

    }

    @GetMapping("/AuthenticationException")
    public void AuthenticationEntryPoint(){

    }
}
