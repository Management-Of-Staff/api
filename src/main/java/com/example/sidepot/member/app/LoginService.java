package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.MemberFactory;
import com.example.sidepot.global.security.RedisUtil;
import com.example.sidepot.global.security.TokenIssuer;
import com.example.sidepot.global.security.TokenType;
import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.dto.AuthDto.*;
import com.example.sidepot.member.util.MemberValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final String GRANT_TYPE_BEARER = "Bearer ";
    private final MemberValidator memberValidator;
    private final TokenIssuer tokenIssuer;
    private final RedisUtil redisUtil;
    private final MemberFactory memberFactory;
    public TokenDto staffLogin(MemberLoginDto memberLoginDto) {
        Staff staff = memberValidator.verifyStaff(memberLoginDto.getPhoneNum());
        memberValidator.checkOwnerWithdrawal(memberLoginDto.getPhoneNum());
        memberValidator.checkPassword(memberLoginDto.getPassword(), staff.getPassword());
        TokenDto tokenDto = TokenDto.from(tokenIssuer.createAccessToken(staff), tokenIssuer.createRefreshToken());
        redisUtil.setRefreshToken(tokenDto.getRefreshToken(), tokenDto.getAccessToken());
        return tokenDto;
    }

    public TokenDto OwnerLogin(MemberLoginDto memberLoginDto) {
        Owner owner = memberValidator.verifyOwner(memberLoginDto.getPhoneNum());
        memberValidator.checkOwnerWithdrawal(memberLoginDto.getPhoneNum());
        memberValidator.checkPassword(memberLoginDto.getPassword(), owner.getPassword());
        TokenDto tokenDto = TokenDto.from(tokenIssuer.createAccessToken(owner), tokenIssuer.createRefreshToken());
        redisUtil.setRefreshToken(tokenDto.getRefreshToken(), tokenDto.getAccessToken());
        return tokenDto;
    }

    public ResponseDto logout(String BearerToken) {
        String accessToken = resolveToken(BearerToken);
        Claims claims = tokenIssuer.parseAccessClaims(accessToken);
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

    public TokenDto reissue(String token) {
        String refreshToken = resolveToken(token);
        if(refreshToken == null){
            throw new Exception(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        Claims claims = tokenIssuer.parseRefreshClaims(refreshToken);
        if (claims == null) {
            throw new Exception(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        claims = tokenIssuer.parseAccessClaims(redisUtil.getRefreshToken(refreshToken));
        Member member = memberFactory.loadMemberByJwtClaims(claims);
        TokenDto tokenDto = TokenDto.from(tokenIssuer.createAccessToken(member), tokenIssuer.createRefreshToken());
        redisUtil.setRefreshToken(TokenType.REFRESH.getDescription() + claims.get("phone").toString(), tokenDto.getRefreshToken());
        return tokenDto;
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(GRANT_TYPE_BEARER.length());
        }
        return null;
    }
}
