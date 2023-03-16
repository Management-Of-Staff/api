package com.example.sidepot.global.security;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberFactory {

    private final StaffRepository staffRepository;
    private final OwnerRepository ownerRepository;

    public Member loadMemberByJwtClaims(Claims claims){
        Role role = Role.valueOf((String) claims.get("roles"));
        String phoneNum = (String) claims.get("phoneNum");
        if(role.equals(Role.STAFF)) return loadStaffByPhoneNum(phoneNum);
        else if(role.equals(Role.OWNER)) return loadOwnerByPhoneNum(phoneNum);
        else throw new Exception(ErrorCode.MEMBER_NOT_FOUND);
    }

    private Staff loadStaffByPhoneNum(String phoneNum){
        return staffRepository.findByMemberPhoneNum(phoneNum)
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Owner loadOwnerByPhoneNum(String phoneNum){
        return ownerRepository.findByMemberPhoneNum(phoneNum)
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }
}
