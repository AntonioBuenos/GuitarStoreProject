package by.smirnov.guitarstoreproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import static by.smirnov.guitarstoreproject.security.SecurityConstants.*;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String login) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(expiration).toInstant());
        return JWT.create()
                .withSubject(JWT_SUBJECT)
                .withClaim(CLAIM_NAME, login)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String ValidateTokenAndRetrieveClaim(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(JWT_SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim(CLAIM_NAME).asString();
    }
}
