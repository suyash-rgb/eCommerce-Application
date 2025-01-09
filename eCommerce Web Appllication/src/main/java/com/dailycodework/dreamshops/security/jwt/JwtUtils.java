package com.dailycodework.dreamshops.security.jwt;

import com.dailycodework.dreamshops.security.user.ShopUserDetails;
import com.dailycodework.dreamshops.utility.KeyGeneratorUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    private final PrivateKey privateKey;

    public JwtUtils() throws Exception{
        KeyPair keyPair = KeyGeneratorUtil.generateECKeyPair();
        this.privateKey = keyPair.getPrivate();
    }

    public String generateTokenForUser(Authentication authentication){
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id", userPrincipal.getId())
                .claim("roles", roles) //any information you wish to encode inside the token for the user pass it as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()+expirationTime)))
                .signWith(key(), SignatureAlgorithm.ES256).compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody().getSubject();
    }

    //method to validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJwt(token);

            return true;
        }catch (ExpiredJwtException | UnsupportedJwtException| MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
