package com.talent.expense_manager.expense_manager.security;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final String apiKey;
//    private final String secrettoken;


    @Autowired
    public AuthenticationService(ApiKeyConfiguration apiKeyConfiguration) {
        this.apiKey = apiKeyConfiguration.getApikey();

    }

    private static final String HEADER_NAME_API_KEY = "X-expense-api-key";
    //private static final String HEADER_NAME_TOKEN_KEY = "expensetoken";


    public Authentication doAuthentication(HttpServletRequest request) {

        String requestApiKey = request.getHeader(HEADER_NAME_API_KEY);


        if (apiKey == null || !requestApiKey.equals(this.apiKey)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
/*
    private void validateToken(String token, String roleFromPath){
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secrettoken)
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            String accountId = claims.get("account_id", String.class);
            if (tokenBlacklistService.isTokenBlacklisted(accountId)) {
                throw new BadCredentialsException("Invalid token. Please provide a valid authentication token.");
            }

            String tokenRole = claims.get("role", String.class);
            if (tokenRole == null || !tokenRole.equals(roleFromPath)) {
                throw new BadCredentialsException("Your token can't access this role type.");
            }

        } catch (JwtException e) {
            if (e instanceof io.jsonwebtoken.ExpiredJwtException) {
                throw new BadCredentialsException("Token has expired. Please obtain a new authentication token.");
            } else {
                throw new BadCredentialsException("Invalid token. Please provide a valid authentication token.");
            }
        }
    }

    private boolean isExcludedURI(String uri) {
        return EXCLUDED_URIS.stream().anyMatch(uri::contains);
    }

    private String extractStringFromPath(String uri, int index) {
        String[] segments = uri.split("/");
        if (segments.length > index) {
            return segments[index];
        } else {
            throw new IllegalArgumentException("Not Found " + index);
        }
    }*/
}

