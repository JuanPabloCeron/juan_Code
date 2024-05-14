package com.juanCode.ProductManagement.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final String SECRET_KEY = "MTc1MDQxOWEwZjUxNTAxNjBkYWRhM2QxZmU4NmIzY2QwM2M3N2FiNjgyMTI5NjE0N2I0NDdiZmY2ZjQ0NWNkZDU1ZmY5NzNjMTRlYTc4NGJkZDE5YTMxMDcxMTU4NWFiZjc2Mzk2NmZmYTc4NGYyOGM2YTc2MGQxMjk5NjY1ZmE=";

    public String getToken(UserDetails user) {

        return getToken(new HashMap<>(),user);
    }

    private  String getToken(Map<String, Object> extraClaims,UserDetails user){
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();

    }

    private Key getKey(){
        byte[] keyByBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByBites);
    }

    //-------------------------------------------------------------------------------------------------

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public  <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }
    }
    //----------
    private Date getExpiration(String token){

        return getClaim(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }


}
