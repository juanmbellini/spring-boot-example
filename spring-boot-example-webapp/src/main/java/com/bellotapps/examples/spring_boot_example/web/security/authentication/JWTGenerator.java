package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.security.TokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Juan Marcos Bellini on 17/10/17.
 * Questions at jbellini@bellotsapps.com
 */
@Component
public class JWTGenerator implements TokenGenerator {

    /**
     * The secret key used to sign the tokens, encoded in base 64.
     */
    private final String base64EncodedSecretKey;

    /**
     * The duration of tokens, in milliseconds.
     */
    private final long duration;

    /**
     * THe signing algorithm used to sign tokens.
     */
    private final SignatureAlgorithm signatureAlgorithm;

    /**
     * Constructor.
     *
     * @param secretKey The secret key used to sign the tokens
     * @param duration  The duration of tokens, in seconds
     */
    public JWTGenerator(@Value("${custom.security.jwt.signing-key}") String secretKey,
                        @Value("${custom.security.jwt.duration}") Long duration) {
        this.base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.duration = duration * 1000;
        this.signatureAlgorithm = SignatureAlgorithm.HS512;
    }

    @Override
    public String generate(User user) {
        Objects.requireNonNull(user, "The user must not be null");

        final Claims claims = Jwts.claims().setSubject(user.getUsername());
        final Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + duration))
                .signWith(signatureAlgorithm, base64EncodedSecretKey)
                .compact();
    }
}
