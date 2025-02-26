package mk.poplaki.service;

import mk.poplaki.dto.auth.RefreshTokenRequest;
import mk.poplaki.dto.auth.TokenRequest;
import mk.poplaki.dto.auth.TokenResponse;

public interface  AuthService {
    TokenResponse getToken(TokenRequest data);
    TokenResponse refreshToken(RefreshTokenRequest data);
}
