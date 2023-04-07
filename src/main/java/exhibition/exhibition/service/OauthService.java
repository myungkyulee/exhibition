package exhibition.exhibition.service;

import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.AuthenticationTokens;
import exhibition.exhibition.dto.SignIn;
import exhibition.exhibition.dto.VisitorInfoFromKakao;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.VisitorRepository;
import exhibition.exhibition.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
    public static final String GET_USER_INFO_URL = "https://kapi.kako.com/v2/user/me";

    private final VisitorRepository visitorRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    public SignIn.Response kakaoSignIn(String accessTokenFromKakao) {
        VisitorInfoFromKakao visitorInfo = getUserInfoFromKakao(accessTokenFromKakao);
        Visitor visitor = visitorRepository.findByEmail(visitorInfo.getEmail())
                .orElse(kakaoSignUp(visitorInfo));

        AuthenticationTokens authenticationTokens = jwtProvider.generateTokens(visitor.getId(), visitor.getRoles());

        return SignIn.Response.builder()
                .name(visitor.getName())
                .tokens(authenticationTokens)
                .build();
    }

    private Visitor kakaoSignUp(VisitorInfoFromKakao visitorInfo) {
        if (visitorRepository.existsByEmail(visitorInfo.getEmail())) {
            throw new ExhibitionException(ErrorCode.DUPLICATED_EMAIL);
        }

        return Visitor.builder()
                .email(visitorInfo.getEmail())
                .name(visitorInfo.getName())
                .visitorName(visitorInfo.getNickname())
                .password(null)
                .roles(List.of("VISITOR"))
                .build();
    }

    private VisitorInfoFromKakao getUserInfoFromKakao(String accessTokenFromKakao) {

        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(GET_USER_INFO_URL))
                .header("Authorization", "Bearer " + accessTokenFromKakao)
                .build();

        return restTemplate.exchange(requestEntity, VisitorInfoFromKakao.class)
                .getBody();
    }
}
