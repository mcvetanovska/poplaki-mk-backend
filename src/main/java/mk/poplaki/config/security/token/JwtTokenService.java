package mk.poplaki.config.security.token;

import mk.poplaki.config.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;

@Slf4j
@Service
public class JwtTokenService implements TokenService {

    private static final String CLAIM_USER_ID = "uid";
    private static final String CLAIM_USER_ROLE = "role";

    private final String accessTokenSecretKey;
    private final long accessTokenValidity;

    private final String refreshTokenSecretKey;
    private final long refreshTokenValidity;

    public JwtTokenService(@Value("${security.jwt.access-token.secret-key}") String accessTokenSecretKey,
                           @Value("${security.jwt.access-token.validity}") long accessTokenValidity,
                           @Value("${security.jwt.refresh-token.secret-key}") String refreshTokenSecretKey,
                           @Value("${security.jwt.refresh-token.validity}") long refreshTokenValidity) {
        this.accessTokenSecretKey = accessTokenSecretKey;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Override
    public String generateAccessToken(CustomUserDetails principal) {
        return generateAccessToken(principal.getId(), principal.getUsername(), principal.getRole());
    }

    @Override
    public String generateRefreshToken(CustomUserDetails principal) {
        return generateRefreshToken(principal.getId(), principal.getUsername());
    }

    @Override
    public Optional<CustomUserDetails> getPrincipalFromAccessToken(String accessToken) {
        try {
            Claims claims = validateToken(accessToken, accessTokenSecretKey);

            CustomUserDetails principal = new CustomUserDetails();
            principal.setId(claims.get(CLAIM_USER_ID, String.class));
            principal.setEmail(claims.getSubject());
            principal.setRole(claims.get(CLAIM_USER_ROLE, String.class));

            return Optional.of(principal);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomUserDetails> getPrincipalFromRefreshToken(String refreshToken) {
        try {
            Claims claims = validateToken(refreshToken, refreshTokenSecretKey);

            CustomUserDetails principal = new CustomUserDetails();
            principal.setId(claims.get(CLAIM_USER_ID, String.class));
            principal.setEmail(claims.getSubject());
            return Optional.of(principal);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String generateAccessToken(String userId, String username, String role) {
        Claims customClaims = Jwts.claims();
        customClaims.put(CLAIM_USER_ID, userId);
        customClaims.put(CLAIM_USER_ROLE, role);
        return generateToken(customClaims, username, getIssuedDate(), getExpirationDate(accessTokenValidity), accessTokenSecretKey);
    }

    private String generateRefreshToken(String userId, String username) {
        Claims customClaims = Jwts.claims();
        customClaims.put(CLAIM_USER_ID, userId);
        return generateToken(customClaims, username, getIssuedDate(), getExpirationDate(refreshTokenValidity), refreshTokenSecretKey);
    }

    private String generateToken(Claims customClaims, String username, Date issuedAt, Date expiration, String secret) {
        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setClaims(customClaims)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();
    }

    private Claims validateToken(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Date getIssuedDate() {
        return new Date();
    }

    private Date getExpirationDate(long validityMillis) {
        if (validityMillis == -1) {
            return null;
        }
        return new Date(System.currentTimeMillis() + validityMillis);
    }
}
