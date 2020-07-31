package com.github.aracwong;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class JwtTest {

    @Test
    public void testJwt() {
        String token = Jwts.builder()
                .setSubject("Alpin-wang")
                .claim("authorities", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "tmax")
                .compact();
        System.out.println(token);

        Claims claims = Jwts.parser()
                .setSigningKey("tmax")
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbHBpbi13YW5nIiwiYXV0aG9yaXRpZXMiOiJhZG1pbiIsImV4cCI6MTU5NjIyMzIzM30.Hn3xewrxnGXyAQK4BfpcqOP9dPJh8H9LgC_gvbCj4gTsLP7i9i85qHGrTLY7Yc1fq9KyliTKNelbuI7NtqZ8Xw")
                .getBody();
        System.out.println(claims);
        System.out.println("username: " + claims.getSubject());
        System.out.println("权限: " + claims.get("authorities"));

    }
}
