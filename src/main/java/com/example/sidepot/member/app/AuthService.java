package com.example.sidepot.member.app;


import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.dto.MemberUpdateDto;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.AuthRepository;
import com.example.sidepot.member.dto.AuthDto.*;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.security.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final String GRANT_TYPE_BEARER = "Bearer";
    private final AuthRepository authRepository;
    private final MemberValidator memberValidator;
    private final TokenIssuer issuer;

    public TokenDto login(MemberLoginDto memberLoginDto)  {
        if(memberValidator.isDeletedMember(memberLoginDto.getPhone())){
            throw new Exception(ErrorCode.ALREADY_DELETED_MEMBER);
        }
        Auth auth = authRepository.findByPhone(memberLoginDto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberValidator.checkPassword(memberLoginDto.getPassword(), auth.getPassword());

        return TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
    }

    public TokenDto reissue(String token) {
        String refreshToken = resolveToken(token);
        if (!StringUtils.hasText(refreshToken)) { log.info("올바르지 않은 헤더"); }

        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) { log.info("토큰 오류"); }

        Auth auth;
        auth = authRepository.findByPhone(claims.getSubject())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth))
                .refreshToken(issuer.createRefreshToken(auth))
                .build();
    }

    @Transactional
    public ResponseDto updateMemberPassword(Auth auth, MemberUpdateDto.MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto){
        String newPassword = memberValidator.encodePassword(memberUpdatePasswordRequestDto.getNewPassword());
        authRepository.updateMemberPassword(auth.getAuthId(), newPassword)
                .orElseThrow(() -> new Exception(ErrorCode.FAILED_UPDATE_PASSWORD));

        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-password"))
                .method("POST")
                .message(String.format("비밀번호 변경 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberPhone(Auth auth, MemberUpdateDto.MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto){
        if(authRepository.existsByPhone(memberUpdatePhoneRequestDto.getPhone())){
            throw new Exception(ErrorCode.PHONE_DUPLICATE);
        }
        authRepository.updateMemberPhone(auth.getAuthId(),
                                         memberUpdatePhoneRequestDto.getPhone(),
                                         memberUpdatePhoneRequestDto.getUUID())
                .orElseThrow(() -> new Exception(ErrorCode.FAILED_UPDATE_PHONE));
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-phone"))
                .method("POST")
                .message(String.format("핸드폰 번호 수정 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateMemberProfile(Auth auth, MemberUpdateDto.MemberUpdateProfileRequestDto memberUpdateProfileRequestDto){

        authRepository.updateMemberProfile(auth.getAuthId(),
                                           LocalDate.parse(memberUpdateProfileRequestDto.getBirthDate(),DateTimeFormatter.ISO_LOCAL_DATE),
                                           memberUpdateProfileRequestDto.getEmail());
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/update-profile"))
                .method("POST")
                .message(String.format("프로필 정보 수정 완료"))
                .statusCode(200)
                .data("")
                .build();
    }
    @Transactional(readOnly = true)
    public ResponseDto checkMemberPassword(Long authId, MemberUpdateDto.MemberCheckPasswordRequestDto memberCheckPasswordRequestDto){
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
    public ResponseDto withdrawalMember(Auth auth){
        authRepository.updateMemberDeleteDate(auth.getAuthId(), LocalDateTime.now());
        return ResponseDto.builder()
                .path(String.format("rest/v1/auth/withdrawal-member"))
                .method("POST")
                .message(String.format("회원탈퇴 처리 완료"))
                .statusCode(200)
                .data("")
                .build();
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(GRANT_TYPE_BEARER.length());
        }
        return null;
    }

}
