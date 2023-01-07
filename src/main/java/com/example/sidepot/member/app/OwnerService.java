package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
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
    public ResponseDto registerOwner(MemberRegisterRequestDto memberRegisterRequestDto) {
        Owner owner = memberValidator.ownerDtoToEntity(memberRegisterRequestDto);
        memberValidator.checkOwnerDuplicate(memberRegisterRequestDto.getPhone());

        if(memberValidator.isDeletedMember(memberRegisterRequestDto.getPhone())){
           throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
        }

        ownerRepository.save(owner);

        return ResponseDto.builder()
                .path(String.format("rest/v1/owner/register"))
                .method("POST")
                .message(String.format("사장님 회원가입 완료"))
                .statusCode(200)
                .data(MemberRegisterResponseDto.builder()
                        .phone(owner.getPhone())
                        .name(owner.getName())
                        .role(owner.getRole())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readOwner(Auth auth){
        Owner owner = ownerRepository.findById(auth.getAuthId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));


        return ResponseDto.builder()
                .path(String.format("rest/v1/owner/"))
                .method("GET")
                .message(String.format("사장님 개인정보 조회 완료"))
                .statusCode(200)
                .data(OwnerReadResponseDto.builder()
                        .phone(owner.getPhone())
                        .name(owner.getName())
                        .UUID(owner.getUUID())
                        .role(owner.getRole())
                        .email(owner.getEmail())
                        .birthDate(owner.getBirthDate())
                        .build())
                .build();

    }

//    @Transactional
//    public void deleteOwner(Auth auth){
//         ownerRepository.deleteById(auth.getAuthId());
//    }
}

