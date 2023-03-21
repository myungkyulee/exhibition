package exhibition.exhibition.provider;

import exhibition.exhibition.util.Aes128Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final long TOKEN_EXPIRED_TIME = 1000 * 60 * 60;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String email) {
        Claims claims = Jwts.claims().setSubject(Aes128Util.encrypt(email));

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
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public String authenticate(String token){
        if (!validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        return getEmail(token);
    }

    public String getEmail(String token) {
        return Aes128Util.decrypt(parseClaims(token).getSubject());
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
