package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.global.file.FileService;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.member.dto.MemberReadDto;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import com.example.sidepot.member.util.MemberValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    private final MemberValidator memberValidator;
    private final @Qualifier(value = "profileService")FileService fileService;
    private final MemberFileRepository memberFileRepository;

    public StaffService(StaffRepository staffRepository, MemberValidator memberValidator,
                        @Qualifier(value = "profileService")FileService fileService,
                        MemberFileRepository memberFileRepository) {
        this.staffRepository = staffRepository;
        this.memberValidator = memberValidator;
        this.fileService = fileService;
        this.memberFileRepository = memberFileRepository;
    }

    @Transactional
    public ResponseDto registerStaff(MemberRegisterRequestDto memberRegisterRequestDto){
        memberValidator.checkStaffDuplicate(memberRegisterRequestDto.getPhone());
        Staff staff = memberValidator.staffDtoToEntity(memberRegisterRequestDto);
        staffRepository.save(staff);
        return ResponseDto.builder()
                .method("POST")
                .statusCode(200)
                .message(String.format("직원 회원가입 완료"))
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readStaff(LoginMember member){
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        return ResponseDto.builder()
                .statusCode(200)
                .method("POST")
                .message(String.format("직원 정보 조회 완료"))
                .data(MemberReadDto.StaffReadResponseDto.of(staff))
                .build();
    }

    @Transactional
    public ResponseDto updateStaffPassword(LoginMember member,
                                           MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto) {
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        String newPassword = memberValidator.encodePassword(memberUpdatePasswordRequestDto.getNewPassword());
        staff.updateMemberPassword(newPassword);
        staffRepository.save(staff);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("비밀번호 변경 성공"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateStaffPhone(LoginMember member,
                                        MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto) {
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        staff.updateMemberPhone(memberUpdatePhoneRequestDto);
        staffRepository.save(staff);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("핸드폰 번호 수정 성공"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateStaffProfile(LoginMember member, MultipartFile profileImage,
                                          MemberUpdateProfileRequestDto memberUpdateProfileRequestDto) throws IOException {
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        BaseFilePath baseFilePath = fileService.saveFileAndGetFileDto(profileImage);
        staff.updateMemberProfile(memberUpdateProfileRequestDto, baseFilePath);
        staffRepository.save(staff);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("프로필 정보 수정 완료"))
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto checkStaffPassword(LoginMember member,
                                          MemberCheckPasswordRequestDto memberCheckPasswordRequestDto) {
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        memberValidator.checkPassword(memberCheckPasswordRequestDto.getPassword(), staff.getPassword());
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("비밀번호 확인 완료"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto withdrawalStaff(LoginMember member){
        Staff staff = memberValidator.verifyStaff(member.getMemberPhoneNum());
        staff.updateMemberWithdrawalDate(LocalDateTime.now());
        staffRepository.save(staff);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.PUT.name())
                .message(String.format("직원 회원탈퇴 완료"))
                .data("")
                .build();
    }
}
