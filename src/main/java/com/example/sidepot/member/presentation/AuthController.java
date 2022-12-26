package com.example.sidepot.member.presentation;



import com.example.sidepot.member.app.AuthService;
import com.example.sidepot.member.dto.AuthDto.*;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class AuthController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
        public TokenDto login(String username) {
            return authService.login(username);
    }
    @PostMapping("/reissue")
    public TokenDto reissue(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken) {
        return authService.reissue(bearerToken);
    }
}

