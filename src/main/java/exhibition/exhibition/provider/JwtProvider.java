package exhibition.exhibition.provider;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
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

    private static final long TOKEN_EXPIRED_TIME = 1000 * 60 * 60;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateToken(Long visitorId, List<String> role) {
        Claims claims = Jwts.claims().setSubject(Aes128Util.encrypt(visitorId.toString()));
        claims.put("role", role);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRED_TIME);

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
