package com.jdt13.hotel.util;

import com.jdt13.hotel.dto.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

public class Jwt {
    private Jwt() {
        throw new IllegalStateException("Jwt class");
    }
    public static String getToken(LoginRequest request) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expireDate = new Date(nowMillis);
        Key key = MacProvider.generateKey();
        return Jwts.builder()
                .setSubject(String.valueOf(request))
                .setAudience("users")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
