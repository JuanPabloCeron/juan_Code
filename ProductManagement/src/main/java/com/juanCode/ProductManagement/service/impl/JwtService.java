package com.juanCode.ProductManagement.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Service

@RequiredArgsConstructor
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




}
