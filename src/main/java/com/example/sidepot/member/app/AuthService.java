package com.example.sidepot.member.app;


import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.AuthRepository;
import com.example.sidepot.member.dto.AuthDto.*;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.security.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final String GRANT_TYPE_BEARER = "Bearer";

    private final AuthRepository authRepository;
    private final MemberValidator memberValidator;
    private final TokenIssuer issuer;

    public TokenDto login(MemberLoginDto memberLoginDto)  {

        Auth auth = authRepository.findByPhone(memberLoginDto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));

        memberValidator.checkPassword(memberLoginDto.getPassword(), auth.getPassword());

        return TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
    }

    public TokenDto reissue(String token) {

        Auth auth;

        String refreshToken = resolveToken(token);
        if (!StringUtils.hasText(refreshToken)) { log.info("올바르지 않은 헤더"); }

        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) { log.info("토큰 오류"); }

        auth = authRepository.findByPhone(claims.getSubject())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
    }

    @Transactional
    public ResponseDto updateMemberPassword(Auth auth, MemberUpdatePasswordRequestDto dto){
        String newPassword = memberValidator.encodePassword(dto.getNewPassword());
        authRepository.updateMemberPassword(auth.getAuthId(), newPassword)
                .orElseThrow(() -> new Exception(ErrorCode.FAILED_UPDATE_PASSWORD));

        return ResponseDto.builder()
                .statusCode(200)
                .message("비밀번호 수정")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberPhone(Auth auth, MemberUpdatePhoneRequestDto dto){
        if(authRepository.existsByPhone(auth.getPhone())){
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }

        authRepository.updateMemberPhone(auth.getAuthId(), dto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.FAILED_UPDATE_PHONE));
        return ResponseDto.builder()
                .statusCode(200)
                .message("핸드폰 번호 수정")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberProfile(Auth auth, MemberUpdateProfileRequestDto dto){
        authRepository.updateMemberProfile(auth.getAuthId(), dto.getBirthDate(), dto.getEmail());
        return ResponseDto.builder()
                .statusCode(200)
                .message("업데이트 완료")
                .build();
    }
    @Transactional(readOnly = true)
    public ResponseDto checkMemberPassword(Long authId, MemberCheckPasswordRequestDto dto){
        Auth auth = authRepository.findById(authId).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberValidator.checkPassword(dto.getPassword(), auth.getPassword());
        return ResponseDto.builder()
                .statusCode(200)
                .method("비밀번호 확인")
                .build();
    }

    @Transactional
    public ResponseDto withdrawalMember(Auth auth, LocalDate withdrawalDate){
        authRepository.updateMemberDeleteDate(auth.getAuthId(), withdrawalDate);
        return ResponseDto.builder()
                .statusCode(200)
                .method("탈퇴처리 완료")
                .build();
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(GRANT_TYPE_BEARER.length());
        }
        return null;
    }

}
