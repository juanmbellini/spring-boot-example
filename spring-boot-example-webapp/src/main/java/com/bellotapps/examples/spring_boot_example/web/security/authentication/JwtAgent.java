package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.security.TokenGenerator;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Object in charge of managing JWT operations (generation and validation).
 */
@Component
public class JwtAgent implements TokenGenerator, JwtParser {

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
    public JwtAgent(@Value("${custom.security.jwt.signing-key}") String secretKey,
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

    @Override
    public Claims parse(String jwtToken) throws IllegalArgumentException {
        if (!StringUtils.hasText(jwtToken)) {
            throw new IllegalArgumentException("The token must not be null or empty");
        }

        try {
            return Jwts.parser()
                    .setSigningKey(base64EncodedSecretKey)
                    .parse(jwtToken, new JwtHandlerAdapter<Jws<Claims>>() {
                        @Override
                        public Jws<Claims> onClaimsJws(Jws<Claims> jws) {
                            final JwsHeader<?> header = jws.getHeader();
                            final Claims claims = jws.getBody();

                            // Check issued at date is present and it is not a future date
                            final Date issuedAt = Optional.ofNullable(claims.getIssuedAt())
                                    .orElseThrow(() ->
                                            new MissingClaimException(header, claims, "Missing \"issued at\" date"));
                            if (issuedAt.after(new Date())) {
                                throw new MalformedJwtException("The \"issued at\" date is a future date");
                            }
                            if (claims.getExpiration() == null) {
                                throw new MissingClaimException(header, claims, "Missing \"expiration\" date");
                            }
                            return jws;
                        }
                    }).getBody();
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | UnsupportedJwtException
                | MissingClaimException e) {
            throw new JwtException("There was a problem with the jwt token", e);
        }
    }

}
