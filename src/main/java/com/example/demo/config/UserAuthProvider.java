package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.DTOS.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO userDTO){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_00);

        return JWT.create()
                .withIssuer(userDTO.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withIssuer("email:" + userDTO.getEmail())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

       JWTVerifier verifier = JWT.require(algorithm).build();
       DecodedJWT decodedJWT = verifier.verify(token);

       UserDTO user = UserDTO.builder()
               .email(decodedJWT.getIssuer())
               .username(decodedJWT.getClaim("username").asString())
               .build();

       return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
