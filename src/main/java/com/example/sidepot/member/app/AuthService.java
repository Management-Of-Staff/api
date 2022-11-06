package com.example.sidepot.member.app;

import com.example.sidepot.member.dto.AuthDto.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberValidator memberValidator;

    @Transactional
    public MemberDto register(MemberDto memberDto){
        memberValidator.checkMemberEmailDuplicate(memberDto);

        return memberDto;


    }

}
