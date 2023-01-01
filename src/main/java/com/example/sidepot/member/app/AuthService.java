package com.example.sidepot.member.app;


import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.security.util.TokenIssuer;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.AuthRepository;
import com.example.sidepot.security.dto.AuthDto.TokenDto;
import com.example.sidepot.security.dto.AuthDto.MemberLoginDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {


    private final String GRANT_TYPE_BEARER = "Bearer";

    private final int BEARER_LENGTH = 7;

    private final AuthRepository authRepository;

    private final MemberValidator memberValidator;
    private final TokenIssuer issuer;

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(BEARER_LENGTH);
        }
        return null;
    }

    private TokenDto createTokenDto(Auth auth) {
        String userName = auth.getName();
        String authority = String.valueOf(auth.getRole().getAuthority());
        return TokenDto.builder()
                .accessToken(issuer.createAccessToken(auth, authority))
                .refreshToken(issuer.createRefreshToken(auth, authority))
                .build();
    }

    public TokenDto login(MemberLoginDto memberLoginDto)  {

        Auth auth = authRepository.findByPhone(memberLoginDto.getPhone())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        memberValidator.checkPassword(memberLoginDto.getPassword(), auth.getPassword());

        return createTokenDto(auth);
    }

    public TokenDto reissue(String token) throws Throwable {

        Auth user;


        String refreshToken = resolveToken(token);
        if (!StringUtils.hasText(refreshToken)) { log.info("올바르지 않은 헤더"); }

        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) { log.info("토큰 오류"); }

        user = authRepository.findByPhone(claims.getSubject())
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없음"));

        return createTokenDto(user);
    }




}
