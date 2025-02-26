package mk.poplaki.config.security.token;

import mk.poplaki.config.security.user.CustomUserDetails;

import java.util.Optional;

public interface TokenService {
    String generateAccessToken(CustomUserDetails principal);
    String generateRefreshToken(CustomUserDetails principal);

    Optional<CustomUserDetails> getPrincipalFromAccessToken(String accessToken);
    Optional<CustomUserDetails> getPrincipalFromRefreshToken(String refreshToken);
}
