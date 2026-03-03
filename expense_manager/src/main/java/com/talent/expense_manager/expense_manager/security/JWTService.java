package com.talent.expense_manager.expense_manager.security;

import com.talent.expense_manager.expense_manager.exception.JwtTokenExpiredException;
import com.talent.expense_manager.expense_manager.model.Account;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String secretKey;


    @Value("${jwt.accesstoken-life}")
    private Long accessTokenLife;

    @Value("${jwt.refreshtoken-life}")
    private Long refreshTokenLife;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("role", account.getRole().getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenLife))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("role", account.getRole().getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenLife))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUsername(String token) {
      try{
          return Jwts.parserBuilder()
                  .setSigningKey(getSecretKey())
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .getSubject();
      }catch (ExpiredJwtException e){
          throw new JwtTokenExpiredException("JWT token is expired");
      }
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());

    }
}
