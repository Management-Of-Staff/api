package com.example.sidepot.member.app;

import com.example.sidepot.member.dto.AuthDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void checkMemberEmailDuplicate(AuthDto.MemberDto memberDto){


    }
}
