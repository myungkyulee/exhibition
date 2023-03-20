package exhibition.exhibition.controller;

import exhibition.exhibition.domain.User;
import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.dto.CreateUser;
import exhibition.exhibition.provider.JwtProvider;
import exhibition.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userservice;
    private final JwtProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<CreateUser.Response> signUp(
            @RequestBody @Valid CreateUser.Request request) {
        log.info("[UserController] signUp");
        return ResponseEntity.ok(userservice.join(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<Authentication.Response> signIn(
            @RequestBody @Valid Authentication.Request request) {
        User user = userservice.signIn(request);
        String jwt = tokenProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(
                Authentication.Response.builder()
                        .username(user.getName())
                        .jwt(jwt)
                        .build()
        );
    }
}
