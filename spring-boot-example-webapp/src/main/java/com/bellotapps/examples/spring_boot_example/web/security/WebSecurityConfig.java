package com.bellotapps.examples.spring_boot_example.web.security;

import com.bellotapps.examples.spring_boot_example.web.controller.rest_endpoints.SessionEndpoint;
import com.bellotapps.examples.spring_boot_example.web.security.authentication.JwtAuthenticationFilter;
import com.bellotapps.examples.spring_boot_example.web.security.authentication.JwtAuthenticationProvider;
import com.bellotapps.examples.spring_boot_example.web.security.authentication.TokenAuthenticationFailureHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedList;
import java.util.List;

/**
 * Spring Security web configuration.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {

    /**
     * The root path in which the jersey application is listening.
     */
    private final String jerseyApplicationPath;

    /**
     * The {@link JwtAuthenticationFilter} to perform JWT authentication.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * The {@link JwtAuthenticationProvider} that authenticates request by a given JWT.
     */
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    @Autowired
    public WebSecurityConfig(@Value("${spring.jersey.application-path:}") String jerseyApplicationPath,
                             JwtAuthenticationProvider jwtAuthenticationProvider,
                             TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler)
            throws Exception {
        this.jerseyApplicationPath = jerseyApplicationPath;

        this.jwtAuthenticationFilter =
                new JwtAuthenticationFilter(passwordAuthenticationMatcher(), optionalAuthenticationMatchers(),
                        tokenAuthenticationFailureHandler);
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.jwtAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .rememberMe().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * @return An {@link AntPathRequestMatcher} for the password authentication endpoint.
     */
    private AntPathRequestMatcher passwordAuthenticationMatcher() {
        return new AntPathRequestMatcher(jerseyApplicationPath +
                SessionEndpoint.SESSIONS_ENDPOINT + SessionEndpoint.LOGIN_ENDPOINT, "POST");
    }

    /**
     * @return A {@link List} of {@link AntPathRequestMatcher}
     * for those endpoints where authentication is not mandatory.
     */
    private List<RequestMatcher> optionalAuthenticationMatchers() {
        return new LinkedList<>();
    }
}
