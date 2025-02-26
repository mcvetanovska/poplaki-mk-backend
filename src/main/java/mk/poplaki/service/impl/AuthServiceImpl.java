package mk.poplaki.service.impl;

import mk.poplaki.config.exception.UnauthorizedException;
import mk.poplaki.config.security.token.JwtTokenService;
import mk.poplaki.config.security.user.CustomUserDetails;
import mk.poplaki.dto.auth.RefreshTokenRequest;
import mk.poplaki.dto.auth.TokenRequest;
import mk.poplaki.dto.auth.TokenResponse;
import mk.poplaki.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService tokenService;

    @Override
    public TokenResponse getToken(TokenRequest data) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            return new TokenResponse(tokenService.generateAccessToken(principal), tokenService.generateRefreshToken(principal), principal.getRole());
        } catch (Exception e) {
            log.error("cannot authenticate user", e);
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest data) {
        Optional<CustomUserDetails> principalOptional = tokenService.getPrincipalFromRefreshToken(data.getRefreshToken());
        if (principalOptional.isEmpty()) {
            log.error("cannot get principal from refresh token");
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        CustomUserDetails principal = (CustomUserDetails) userDetailsService.loadUserByUsername(principalOptional.get().getEmail());

        return new TokenResponse(tokenService.generateAccessToken(principal), data.getRefreshToken(), principal.getRole());
    }
}
