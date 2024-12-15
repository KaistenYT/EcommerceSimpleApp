package ks.dev.ShoppingCartApp.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ks.dev.ShoppingCartApp.security.user.ShopUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;



@Component
public class JwtUtils {


    @Value("${auth.token.jwtSecret}")
    private String jwTSecret;
    @Value("${auth.token.expirationInMils}")
    private int expirationTime;


    public String generateTokenForUser(Authentication authentication){
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id",userPrincipal.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwTSecret));
    }
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
    public  boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());

        }
    }
}
