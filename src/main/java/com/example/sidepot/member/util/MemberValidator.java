package com.example.sidepot.member.util;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberValidator {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OwnerRepository ownerRepository;
    private final StaffRepository staffRepository;

    @Transactional(readOnly = true)
    public void checkOwnerDuplicate(String phone){
        if(ownerRepository.existsByPhone(phone)){
            throw new Exception(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public void checkStaffDuplicate(String phone){
        if(staffRepository.existsByPhone(phone)){
            throw new Exception(ErrorCode.EMAIL_DUPLICATE);
        }
    }
    @Transactional(readOnly = true)
    public Owner ownerDtoToEntity(MemberDto.OwnerDto ownerDto){
        return Owner.of(ownerDto.getName(), ownerDto.getPhone(), encodePassword(ownerDto.getPassword()), ownerDto.getRole());
    }

    @Transactional(readOnly = true)
    public Staff staffDtoToEntity(MemberDto.StaffDto staffDto){
        return Staff.of(staffDto.getName(), staffDto.getPhone(), encodePassword(staffDto.getPassword()), staffDto.getRole());
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public void checkPassword(String rawPassword, String encodePassword){
        if(!bCryptPasswordEncoder
                .matches(rawPassword, encodePassword)) {
            throw new Exception(ErrorCode.MEMBER_PASSWORD);
        }
    }
}
