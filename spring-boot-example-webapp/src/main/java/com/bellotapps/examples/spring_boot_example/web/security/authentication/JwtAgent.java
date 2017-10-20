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
public class JwtAgent implements TokenGenerator, JwtCompiler {

    private final static String USER_ID_CLAIM_NAME = "uid";

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

        final Claims claims = Jwts.claims();
        claims.put(USER_ID_CLAIM_NAME, user.getId());
        claims.setSubject(user.getUsername());
        final Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + duration))
                .signWith(signatureAlgorithm, base64EncodedSecretKey)
                .compact();
    }

    @Override
    public JwtTokenData compile(String rawToken) throws IllegalArgumentException {
        if (!StringUtils.hasText(rawToken)) {
            throw new IllegalArgumentException("The token must not be null or empty");
        }

        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(base64EncodedSecretKey)
                    .parse(rawToken, CustomJwtHandlerAdapter.getInstance())
                    .getBody();

            // TODO: check blacklisted

            final long userId = (long) claims.get(USER_ID_CLAIM_NAME);  // Previous step validated this value.
            final String username = claims.getSubject();
            return new JwtTokenData(userId, username);
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | UnsupportedJwtException
                | MissingClaimException e) {
            throw new JwtException("There was a problem with the jwt token", e);
        }
    }


    /**
     * Custom implementation of {@link JwtHandlerAdapter}.
     */
    private static class CustomJwtHandlerAdapter extends JwtHandlerAdapter<Jws<Claims>> {

        /**
         * Single instance of this class.
         */
        private static CustomJwtHandlerAdapter singleton = new CustomJwtHandlerAdapter();

        /**
         * @return The single instance of this class.
         */
        private static CustomJwtHandlerAdapter getInstance() {
            return singleton;
        }


        @Override
        public Jws<Claims> onClaimsJws(Jws<Claims> jws) {
            final JwsHeader<?> header = jws.getHeader();
            final Claims claims = jws.getBody();

            // Check user id is not missing
            final Object userIdObject = claims.get(USER_ID_CLAIM_NAME);
            if (userIdObject == null) {
                throw new MissingClaimException(header, claims, "Missing \"user id\" claim");
            }
            // Check user id is an integer (or long) number
            if (!(userIdObject instanceof Integer) && !(userIdObject instanceof Long)) {
                throw new MalformedJwtException("The \"user id\" claim must be an integer or a long");
            }
            // Transform the user id from integer to long
            if (userIdObject instanceof Integer) {
                final long userId = ((Integer) userIdObject).longValue();
                claims.put(USER_ID_CLAIM_NAME, userId);
            }

            // Check issued at date is present and it is not a future date
            final Date issuedAt = Optional.ofNullable(claims.getIssuedAt())
                    .orElseThrow(() ->
                            new MissingClaimException(header, claims, "Missing \"issued at\" date"));
            if (issuedAt.after(new Date())) {
                throw new MalformedJwtException("The \"issued at\" date is a future date");
            }
            // Check expiration date is not missing
            if (claims.getExpiration() == null) {
                throw new MissingClaimException(header, claims, "Missing \"expiration\" date");
            }

            return jws;
        }
    }
}
