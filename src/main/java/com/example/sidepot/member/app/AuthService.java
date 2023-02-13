package com.example.sidepot.member.app;


import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.filehandle.CreateBaseFileDto;
import com.example.sidepot.global.filehandle.FileHandler;
import com.example.sidepot.global.filehandle.FileType;
import com.example.sidepot.global.security.RedisUtil;
import com.example.sidepot.global.security.TokenType;
import com.example.sidepot.member.domain.MemberFile;
import com.example.sidepot.member.domain.MemberFileRepository;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.AuthRepository;
import com.example.sidepot.member.dto.AuthDto.*;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.global.security.TokenIssuer;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;


@Slf4j
@Service
public class AuthService {

    private final String GRANT_TYPE_BEARER = "Bearer ";
    private final AuthRepository authRepository;
    private final MemberValidator memberValidator;
    private final TokenIssuer issuer;
    private final RedisUtil redisUtil;
    private final MemberFileRepository fileRepository;
    private final FileHandler fileHandler;

    public AuthService(AuthRepository authRepository, MemberValidator memberValidator,
                       TokenIssuer issuer, RedisUtil redisUtil, MemberFileRepository fileRepository,
                       @Qualifier(value = "profileFileService")FileHandler fileHandler) {
        this.authRepository = authRepository;
        this.memberValidator = memberValidator;
        this.issuer = issuer;
        this.redisUtil = redisUtil;
        this.fileRepository = fileRepository;
        this.fileHandler = fileHandler;
    }

    public TokenDto login(MemberLoginDto memberLoginDto) {
        if (memberValidator.isDeletedMember(memberLoginDto.getPhone())) {
            throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
        }
        Auth auth = authRepository.findByPhone(memberLoginDto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberValidator.checkPassword(memberLoginDto.getPassword(), auth.getPassword());
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
        redisUtil.setRefreshToken(TokenType.REFRESH.getDescription() + memberLoginDto.getPhone(), tokenDto.getRefreshToken());
        return tokenDto;
    }

    public ResponseDto logout(String BearerToken) {
        String accessToken = resolveToken(BearerToken);
        Claims claims = issuer.parseAccessClaims(accessToken);
        redisUtil.setBlackList(accessToken, claims.getExpiration());
        redisUtil.deleteRefreshToken(TokenType.REFRESH.getDescription() + claims.get("phone").toString());
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/logout"))
                .method("POST")
                .message(String.format("로그아웃 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    public TokenDto reissue(String BearerToken) {
        String refreshToken = resolveToken(BearerToken);
        if (!StringUtils.hasText(refreshToken)) {
            log.info("올바르지 않은 헤더");
        }
        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) {
            log.info("토큰 오류");
        }
        Auth auth;
        auth = authRepository.findByPhone(claims.getSubject())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
        redisUtil.setRefreshToken(TokenType.REFRESH.getDescription() + claims.get("phone").toString(), tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Transactional
    public ResponseDto updateMemberPassword(Auth auth, MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto) {
        Auth member = authRepository.findByPhone(auth.getPhone()).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        String newPassword = memberValidator.encodePassword(memberUpdatePasswordRequestDto.getNewPassword());
        member.updateMemberPassword(newPassword);
        authRepository.save(member);
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-password"))
                .method("POST")
                .message(String.format("비밀번호 변경 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberPhone(Auth auth, MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto) {
        Auth member = authRepository.findByPhone(auth.getPhone()).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        if (authRepository.existsByPhone(memberUpdatePhoneRequestDto.getPhone())) {
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
        member.updateMemberPhone(memberUpdatePhoneRequestDto);
        authRepository.save(member);
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-phone"))
                .method("POST")
                .message(String.format("핸드폰 번호 수정 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberProfile(Auth auth, MultipartFile profileImage,
                                           MemberUpdateProfileRequestDto memberUpdateProfileRequestDto) throws IOException {
        Auth member = authRepository.findByPhone(auth.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        getBaseFileDto(profileImage);
        member.updateMemberProfile(memberUpdateProfileRequestDto);
        authRepository.save(member);
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-profile"))
                .method("POST")
                .message(String.format("프로필 정보 수정 완료"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto checkMemberPassword(Long authId, MemberCheckPasswordRequestDto memberCheckPasswordRequestDto) {
        Auth auth = authRepository.findById(authId).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberValidator.checkPassword(memberCheckPasswordRequestDto.getPassword(), auth.getPassword());
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/check-password"))
                .method("POST")
                .message(String.format("비밀번호 확인 완료"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto withdrawalMember(Auth auth) {
        Auth member = authRepository.findByPhone(auth.getPhone()).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        if (member.getDeleteDate() != null) {
            throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
        }
        member.updateMemberDeleteDate(LocalDateTime.now());
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/withdrawal-member"))
                .method("POST")
                .message(String.format("회원탈퇴 처리 완료"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public void getBaseFileDto(MultipartFile profileImage) throws IOException {
        for(CreateBaseFileDto dto :fileHandler.saveFileAndGetFileDto(profileImage)){
            fileRepository.save(MemberFile
                    .builder()
                    .fileType(FileType.PROFILE)
                    .fileOriginName(dto.getFileOriginName())
                    .fileSaveName(dto.getFileSavePath()).build());
        }
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(GRANT_TYPE_BEARER.length());
        }
        return null;
    }
}
