package exhibition.exhibition.controller;

import exhibition.exhibition.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/kakao/callback")
    public ResponseEntity<?> kakaoSignIn(String accessTokenFromKakao) {
        return ResponseEntity.ok(oauthService.kakaoSignIn(accessTokenFromKakao));
    }
}
