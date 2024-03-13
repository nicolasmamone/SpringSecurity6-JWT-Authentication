package com.nico.jwt.service;

import com.nico.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // genera el token
    private String getToken(HashMap<String, Objects> extraClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))  //fecha de expiracion 24horas
                .signWith(getKey(), SignatureAlgorithm.HS256) //firma
                .compact(); // para que cree el objeto y lo serialize
    }

    private Key getKey() {
        //convertimos la secretkey en base 64
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // nos permite crear una nueva instancia de nuestra secretKey

    }

    public String getUsernameFronToken(String token) {
        return getClaims(token, Claims::getSubject);
    }
    //comparamos el username del token con el userdetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFronToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //metodo privado que va a obtener todos los claim del token
    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //metodo generico que me permite obtener un claim en particular
    public<T> T getClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //obtengo la expiracion
    private Date getExpiration(String token){
        return getClaims(token, Claims::getExpiration);
    }
    // verifico si expiro pasandole la fecha actual
    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
