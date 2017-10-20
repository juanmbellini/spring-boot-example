package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@link AbstractAuthenticationProcessingFilter} in charge of performing JWT authentication.
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String AUTHENTICATION_SCHEME_WITH_SPACE = AUTHENTICATION_SCHEME + " ";

    /**
     * Default constructor.
     */
    public JwtAuthenticationFilter() {
        super("/**");
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // Nothing done
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        final String authorizationHeader = request.getHeader(AUTHENTICATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME_WITH_SPACE)) {
            throw new MissingJwtException(AUTHENTICATION_HEADER);
        }
        final String jwtRawToken = authorizationHeader.substring(AUTHENTICATION_SCHEME_WITH_SPACE.length());
        final JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwtRawToken);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // Continue with normal flow
        chain.doFilter(request, response);
    }
}
