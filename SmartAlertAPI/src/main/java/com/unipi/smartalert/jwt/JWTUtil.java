package com.unipi.smartalert.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {
    private static final String SECRET_KEY =
            "foobar123456789foobar123456789foobar123456789foobar123456789";

    private int jwtExpirationMs = 3600000;

    public String issueToken(String subject){
        return issueToken(subject, Map.of());
    }
    //for extra claims
    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims){
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://amigoscode.com")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(1, ChronoUnit.DAYS)
                        )
                ).signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }

    public String extractIdToken(String tokenResponse) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(tokenResponse, new TypeReference<Map<String, Object>>() {});
        if (!responseMap.containsKey("id_token")) {
            throw new IllegalArgumentException("Response does not contain id_token field");
        }
        return (String) responseMap.get("id_token");
    }

    public Map<String, Object> decodeGoogleIdToken(String idToken) throws Exception {
        String[] parts = idToken.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid ID Token format");
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        String payload = new String(decodedBytes);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
    }
}
