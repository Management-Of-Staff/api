package com.example.sidepot.member.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.dto.MemberReadDto.*;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service
public class OwnerService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;

    @Transactional
    public MemberRegisterResponseDto registerOwner(MemberRegisterRequestDto dto) {
        Owner owner = memberValidator.ownerDtoToEntity(dto);
        memberValidator.checkOwnerDuplicate(dto.getPhone());

        if(memberValidator.isDeletedMember(dto.getPhone())){
           throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
        }

        ownerRepository.save(owner);
        return MemberRegisterResponseDto.builder()
                .phone(owner.getPhone())
                .name(owner.getName())
                .UUID(owner.getUUID())
                .role(owner.getRole())
                .build();
    }

    @Transactional(readOnly = true)
    public OwnerReadResponseDto readOwner(Auth auth){
        Owner owner = ownerRepository.findById(auth.getId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return OwnerReadResponseDto.builder()
                .phone(owner.getPhone())
                .name(owner.getName())
                .UUID(owner.getUUID())
                .role(owner.getRole())
                .email(owner.getEmail())
                .birthDate(owner.getBirthDate())
                .build();
    }

    @Transactional
    public void deleteOwner(Auth auth){
         ownerRepository.deleteById(auth.getId());
    }
}

