package com.example.sidepot.security.app;


import com.example.sidepot.security.error.TokenException;
import com.example.sidepot.security.util.TokenIssuer;
import com.example.sidepot.security.domain.Auth;
import com.example.sidepot.security.domain.AuthRepository;
import com.example.sidepot.security.dto.AuthDto.TokenDto;
import com.example.sidepot.security.dto.AuthDto.MemberLoginDto;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


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
                .accessToken(issuer.createAccessToken(auth, authority))
                .refreshToken(issuer.createRefreshToken(auth, authority))
                .build();
    }

    public TokenDto login(MemberLoginDto memberLoginDto)  {

        Auth auth = authRepository.findByPhone(memberLoginDto.getPhone())
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없음"));
        return createTokenDto(auth);
    }

    public TokenDto reissue(String token) throws Throwable {

        Auth user;


        String refreshToken = resolveToken(token);
        if (!StringUtils.hasText(refreshToken)) { throw new TokenException(" "); }

        Claims claims = issuer.parseRefreshClaims(refreshToken);
        if (claims == null) { throw new TokenException(" "); }

        user = authRepository.findByPhone(claims.getSubject())
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없음"));

        return createTokenDto(user);
    }
}
