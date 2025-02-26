package mk.poplaki.config.security;

import mk.poplaki.config.security.token.TokenService;
import mk.poplaki.config.security.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_VALUE = "authorization";
    private static final String TOKEN_TYPE_REGEX = "(?i)bearer";
    private static final String TOKEN_TYPE_REPLACEMENT = "";

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<CustomUserDetails> principalOptional = getPrincipal(request);
        principalOptional.ifPresent(principal -> {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });

        filterChain.doFilter(request, response);
    }

    private Optional<CustomUserDetails> getPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER_VALUE))
                .filter(val -> !val.trim().isEmpty())
                .map(val -> val.replaceFirst(TOKEN_TYPE_REGEX, TOKEN_TYPE_REPLACEMENT).trim())
                .flatMap(tokenService::getPrincipalFromAccessToken);
    }
}
