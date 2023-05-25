package com.example.sidepot.command.member.app;

import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.OwnerRepository;
import com.example.sidepot.command.member.dto.MemberReadDto;
import com.example.sidepot.command.member.dto.MemberRegisterDto;
import com.example.sidepot.command.member.dto.MemberUpdateDto;
import com.example.sidepot.command.member.util.MemberValidator;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.global.file.FileService;
import com.example.sidepot.global.security.LoginMember;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class OwnerService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;
    private final FileService fileService;

    public OwnerService(MemberValidator memberValidator, OwnerRepository ownerRepository,
                        @Qualifier(value = "profileService")FileService fileService) {
        this.memberValidator = memberValidator;
        this.ownerRepository = ownerRepository;
        this.fileService = fileService;
    }

    @Transactional
    public ResponseDto registerOwner(MemberRegisterDto.MemberRegisterRequestDto memberRegisterRequestDto) {
        memberValidator.checkOwnerDuplicate(memberRegisterRequestDto.getPhone());
        Owner owner = memberValidator.ownerDtoToEntity(memberRegisterRequestDto);
        ownerRepository.save(owner);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("사장님 회원가입 완료"))
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readOwner(LoginMember member){
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.GET.name())
                .message(String.format("사장님 개인정보 조회 완료"))
                .data(MemberReadDto.OwnerReadResponseDto.of(owner))
                .build();
    }

    @Transactional
    public ResponseDto updateOwnerPassword(LoginMember member, MemberUpdateDto.MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto) {
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        String newPassword = memberValidator.encodePassword(memberUpdatePasswordRequestDto.getNewPassword());
        owner.updateMemberPassword(newPassword);
        ownerRepository.save(owner);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("비밀번호 변경 성공"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateOwnerPhone(LoginMember member, MemberUpdateDto.MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto) {
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        owner.updateMemberPhone(memberUpdatePhoneRequestDto);
        ownerRepository.save(owner);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("핸드폰 번호 수정 성공"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateOwnerProfile(LoginMember member, MultipartFile profileImage,
                                          MemberUpdateDto.MemberUpdateProfileRequestDto memberUpdateProfileRequestDto) throws IOException {
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        BaseFilePath baseFilePath = fileService.saveFileAndGetFileDto(profileImage);
        owner.updateMemberProfile(memberUpdateProfileRequestDto, baseFilePath);
        ownerRepository.save(owner);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("프로필 정보 수정 완료"))
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto checkOwnerPassword(LoginMember member,
                                         MemberUpdateDto.MemberCheckPasswordRequestDto memberCheckPasswordRequestDto) {
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        memberValidator.checkPassword(memberCheckPasswordRequestDto.getPassword(), owner.getPassword());
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message(String.format("비밀번호 확인 완료"))
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto withdrawalOwner(LoginMember member){
        Owner owner = memberValidator.verifyOwner(member.getMemberPhoneNum());
        owner.updateMemberWithdrawalDate(LocalDateTime.now());
        ownerRepository.save(owner);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.PUT.name())
                .message(String.format("직원 회원탈퇴 완료"))
                .data("")
                .build();
    }

}

