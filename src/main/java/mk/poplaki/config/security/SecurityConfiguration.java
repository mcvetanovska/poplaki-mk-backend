package mk.poplaki.config.security;

import mk.poplaki.config.security.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint));
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.SWAGGER).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.ACTUATOR).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.ERROR).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.AUTH).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.REGISTER).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.GET, SecurityPatterns.PUBLIC).permitAll());
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.ADMIN).hasAuthority(Role.ADMIN));
        http.authorizeHttpRequests(req -> req.requestMatchers(SecurityPatterns.ALL).authenticated());

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

