package com.example.sidepot.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    public void setRefreshToken(String key, String value){
        redisTemplate.opsForValue().set(key, value, TokenType.REFRESH.getExpiration(), TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String key){
        redisTemplate.delete(key);
    }
    public void setBlackList(String value, Date expirationTime){
        redisTemplate.opsForValue().set(value, "logout", getExpirationTime(expirationTime), TimeUnit.MILLISECONDS);
    }

    public Long getExpirationTime(Date expiration){
        return(expiration.getTime() - new Date().getTime());
    }

    public boolean isLogout(String accessToken){
         return redisTemplate.hasKey(accessToken);
    }

}
