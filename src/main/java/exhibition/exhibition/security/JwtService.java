package exhibition.exhibition.security;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.dto.AuthenticationTokens;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    public AuthenticationTokens generateAccessTokenFromRefreshToken(String refreshToken) {

        Authentication authentication = jwtProvider.getAuthentication(refreshToken);

        if (!refreshTokenRepository.existToken(refreshToken, authentication.getId())) {
            throw new ExhibitionException(ErrorCode.INVALID_TOKEN);
        }

        refreshTokenRepository.deleteRefreshToken(refreshToken);

        return jwtProvider.generateTokens(authentication.getId(), authentication.getRoles());
    }
}
