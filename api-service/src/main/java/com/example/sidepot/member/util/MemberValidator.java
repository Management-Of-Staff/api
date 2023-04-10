package com.example.sidepot.member.util;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
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
        if(ownerRepository.existsByMemberPhoneNum(phone)){
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public void checkStaffDuplicate(String phone){
        if(staffRepository.existsByMemberPhoneNum(phone)){
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public void checkStaffWithdrawal(String phoneNum){
        if(staffRepository.existsByMemberPhoneNumAndWithdrawalDateIsNotNull(phoneNum))
            throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
    }

    @Transactional(readOnly = true)
    public void checkOwnerWithdrawal(String phoneNum){
        if(ownerRepository.existsByMemberPhoneNumAndWithdrawalDateIsNotNull(phoneNum))
            throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
    }

    public Staff verifyStaff(String phoneNum) {
        return staffRepository.findByMemberPhoneNum(phoneNum)
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Owner verifyOwner(String phoneNum) {
        return ownerRepository.findByMemberPhoneNum(phoneNum)
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Owner ownerDtoToEntity(MemberRegisterRequestDto memberRegisterRequestDto){
        return Owner.registerOwner(memberRegisterRequestDto.getName(),
                                   encodePassword(memberRegisterRequestDto.getPassword()),
                                   memberRegisterRequestDto.getPhone(),
                                   memberRegisterRequestDto.getCreateDate());
    }

    public Staff staffDtoToEntity(MemberRegisterRequestDto memberRegisterRequestDto){
        return Staff.registerStaff(memberRegisterRequestDto.getName(),
                                   encodePassword(memberRegisterRequestDto.getPassword()),
                                   memberRegisterRequestDto.getPhone(),
                                   memberRegisterRequestDto.getCreateDate());
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public void checkPassword(String rawPassword, String encodePassword){
        if(!bCryptPasswordEncoder
                .matches(rawPassword, encodePassword)) {
            throw new Exception(ErrorCode.MEMBER_WRONG_PASSWORD);
        }
    }
}
