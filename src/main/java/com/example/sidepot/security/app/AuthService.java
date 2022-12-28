package com.example.sidepot.security.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.security.error.TokenException;
import com.example.sidepot.security.util.TokenIssuer;
import com.example.sidepot.security.domain.Auth;
import com.example.sidepot.security.domain.AuthRepository;
import com.example.sidepot.security.dto.AuthDto.TokenDto;
import com.example.sidepot.security.dto.AuthDto.MemberLoginDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Optional;

@Service
public class AuthService {


    private final String GRANT_TYPE_BEARER = "Bearer";

    private final int BEARER_LENGTH = 7;

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenIssuer issuer;

    public AuthService(
            AuthRepository authRepository,
            BCryptPasswordEncoder passwordEncoder,
            TokenIssuer issuer) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.issuer = issuer;
    }

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
                .accessToken(issuer.createAccessToken(userName, authority))
                .refreshToken(issuer.createRefreshToken(userName, authority))
                .build();
    }

    public TokenDto login(MemberLoginDto memberLoginDto)  {

        Auth user;
        Optional<Auth> o = authRepository.findByPhone(memberLoginDto.getPhone());

        if(o.isPresent()){
            user = o.get();
        } else{
            throw new Exception(ErrorCode.MEMBER_NOT_FOUND);
        }

        return createTokenDto(user);
    }

    public TokenDto reissue(String bearerToken) throws Throwable {

        Auth user;
        Optional<Auth>  o;

        String refreshToken = resolveToken(bearerToken);
        if (!StringUtils.hasText(refreshToken)) { throw new TokenException(" "); }

        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) { throw new TokenException(" "); }

        o = authRepository.findByPhone(claims.getSubject());

        if(o.isPresent()){
            user = o.get();
        } else{
            throw new Exception(ErrorCode.MEMBER_NOT_FOUND);
        }
        return createTokenDto(user);
    }
}
