package com.example.blps_2.security;

import com.example.blps_2.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTProvider {
    @Value("${JWT_SECRET}")
    private String SECRET;
    @Value("${EXPIRED}")
    private int EXPIRED_TIME;

    public String generateToken(User user) {
        var expirationTime = Date.from(ZonedDateTime.now().plusHours(EXPIRED_TIME).toInstant());
        var admin = false;
        for (var role : user.getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                admin = true;
                break;
            }
        }
        String role = admin ? "ROLE_ADMIN" : "ROLE_USER";

        return JWT.create().withSubject("User details").withClaim("username", user.getUsername()).withClaim("role", role)
                .withIssuedAt(new Date()).withIssuer(SECRET).withExpiresAt(expirationTime).sign(Algorithm.HMAC256(SECRET));
    }

    public Map<String, String> validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        var verifier = JWT.require(Algorithm.HMAC256(SECRET)).withSubject("User details").withIssuer(SECRET).build();

        var jwt = verifier.verify(token);
        var data = new HashMap<String, String>();
        data.put("username", jwt.getClaim("username").asString());
        data.put("role", jwt.getClaim("role").asString());
        System.out.println(data);

        return data;
    }
}
