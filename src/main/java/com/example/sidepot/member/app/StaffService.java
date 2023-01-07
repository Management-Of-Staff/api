package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.member.dto.MemberReadDto.*;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public ResponseDto registerStaff(MemberRegisterRequestDto dto){
        memberValidator.checkStaffDuplicate(dto.getPhone());
        Staff staff = memberValidator.staffDtoToEntity(dto);
        staffRepository.save(staff);

        return ResponseDto.builder()
                .path(String.format("rest/v1/staffs/register"))
                .method("POST")
                .message(String.format("직원 회원가입 완료"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readStaff(Auth auth){
        Staff staff = staffRepository.findById(auth.getAuthId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return ResponseDto.builder()
                .path(String.format("rest/v1/staffs/"))
                .method("POST")
                .message(String.format("직원 정보 조회 완료"))
                .statusCode(200)
                .data(StaffReadResponseDto.builder()
                        .phone(staff.getPhone())
                        .name(staff.getName())
                        .UUID(staff.getUUID())
                        .role(staff.getRole())
                        .email(staff.getEmail())
                        .birthDate(staff.getBirthDate())
                        .build())
                .build();

       
    }

//    @Transactional
//    public void deleteStaff(Auth auth){
//        staffRepository.deleteById(auth.getAuthId());
//    }
}
