package com.example.sidepot.member.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.OwnerRepoitory;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final PasswordEncoder passwordEncoder;
    private final OwnerRepoitory memberRepoitory;

    @Transactional
    public void checkMemberDuplicate(String phone){
        if(memberRepoitory.existsByPhone(phone)){
            throw new Exception(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    @Transactional
    public Owner ownerDtoToEntity(OwnerDto ownerDto){
        return Owner.of(ownerDto.getName(), ownerDto.getPhone(), encodePassword(ownerDto.getPassword()));
    }

    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
