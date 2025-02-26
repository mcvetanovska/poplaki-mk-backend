package mk.poplaki.controller;

import mk.poplaki.dto.auth.RefreshTokenRequest;
import mk.poplaki.dto.auth.TokenRequest;
import mk.poplaki.dto.auth.TokenResponse;
import mk.poplaki.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("token")
    public TokenResponse getToken(@RequestBody TokenRequest data) {
        return authService.getToken(data);
    }

    @PutMapping("token")
    public TokenResponse refreshToken(@RequestBody RefreshTokenRequest data) {
        return authService.refreshToken(data);
    }

}
