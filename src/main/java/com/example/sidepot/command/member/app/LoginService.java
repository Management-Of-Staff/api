package com.example.sidepot.command.member.app;

import com.example.sidepot.command.member.domain.Member;
import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.dto.AuthDto;
import com.example.sidepot.command.member.util.MemberValidator;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.MemberFactory;
import com.example.sidepot.global.security.RedisUtil;
import com.example.sidepot.global.security.TokenIssuer;
import com.example.sidepot.global.security.TokenType;
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
    public AuthDto.TokenDto staffLogin(AuthDto.MemberLoginDto memberLoginDto) {
        Staff staff = memberValidator.verifyStaff(memberLoginDto.getPhoneNum());
        memberValidator.checkOwnerWithdrawal(memberLoginDto.getPhoneNum());
        memberValidator.checkPassword(memberLoginDto.getPassword(), staff.getPassword());
        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.from(tokenIssuer.createAccessToken(staff), tokenIssuer.createRefreshToken());
        redisUtil.setRefreshToken(tokenDto.getRefreshToken(), tokenDto.getAccessToken());
        return tokenDto;
    }

    public AuthDto.TokenDto OwnerLogin(AuthDto.MemberLoginDto memberLoginDto) {
        Owner owner = memberValidator.verifyOwner(memberLoginDto.getPhoneNum());
        memberValidator.checkOwnerWithdrawal(memberLoginDto.getPhoneNum());
        memberValidator.checkPassword(memberLoginDto.getPassword(), owner.getPassword());
        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.from(tokenIssuer.createAccessToken(owner), tokenIssuer.createRefreshToken());
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

    public AuthDto.TokenDto reissue(String token) {
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
        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.from(tokenIssuer.createAccessToken(member), tokenIssuer.createRefreshToken());
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
