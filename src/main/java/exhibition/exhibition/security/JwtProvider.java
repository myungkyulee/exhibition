package exhibition.exhibition.security;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.dto.AuthenticationTokens;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.RefreshTokenRepository;
import exhibition.exhibition.util.Aes128Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    // refresh token 만료시간 일주일
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 7;
    // access token 만료시간 1시간
    private static final long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 60;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public AuthenticationTokens generateTokens(Long visitorId, List<String> role) {

        String accessToken = generateToken(visitorId, role, ACCESS_TOKEN_EXPIRED_TIME);
        String refreshToken = generateToken(visitorId, role, REFRESH_TOKEN_EXPIRED_TIME);

        refreshTokenRepository.saveRefreshToken(refreshToken, visitorId);

        return new AuthenticationTokens(accessToken, refreshToken);
    }

    public String generateToken(Long visitorId, List<String> role, long expiredTime) {
        Claims claims = Jwts.claims().setSubject(Aes128Util.encrypt(visitorId.toString()));
        claims.put("role", role);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public Authentication getAuthentication(String token) {
        if (!validateToken(token)) {
            throw new ExhibitionException(ErrorCode.INVALID_TOKEN);
        }
        Claims claims = parseClaims(token);
        long visitorId = Long.parseLong(Objects.requireNonNull(Aes128Util.decrypt(claims.getSubject())));
        List<String> roles = (List<String>) claims.get("role");

        return new Authentication(visitorId, roles);
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;
        Claims claims = parseClaims(token);

        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
