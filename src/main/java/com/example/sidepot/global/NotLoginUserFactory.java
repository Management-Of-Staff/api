package com.example.sidepot.global;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.AuthRepository;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.domain.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@RequiredArgsConstructor
@Component
public class NotLoginUserFactory {
    private final AuthRepository authRepository;

    private final OwnerRepository ownerRepository;

    private final StaffRepository staffRepository;

    private long id;


    public Auth readUser(){
        return authRepository.findByAuthId(1L).orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Auth readUser(Long id){
        return authRepository.findByAuthId(id).orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Auth readUser(String phone){
        return authRepository.findByPhone(phone).orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }
}
