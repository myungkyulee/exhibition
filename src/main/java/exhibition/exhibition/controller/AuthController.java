package exhibition.exhibition.controller;

import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.dto.SignIn;
import exhibition.exhibition.dto.CreateUser;
import exhibition.exhibition.provider.JwtProvider;
import exhibition.exhibition.service.AuthorService;
import exhibition.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthorService authorService;
    private final JwtProvider jwtProvider;
    private final UserService userservice;

    @PostMapping("/signUp")
    public ResponseEntity<CreateUser.Response> signUp(
            @RequestBody @Valid CreateUser.Request request) {
        log.info("[UserController] signUp");
        return ResponseEntity.ok(userservice.join(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignIn.Response> signIn(
            @RequestBody @Valid SignIn.Request request) {
        return ResponseEntity.ok(userservice.signIn(request));
    }

    @PostMapping("/createAuthor")
    public ResponseEntity<CreateAuthor.Response> createAuthor(
            @RequestBody @Valid CreateAuthor.Request authorForm,
            @RequestHeader("Authorization") String token) {

        Long userId = jwtProvider.getAuthentication(token).getId();

        CreateAuthor.Response result =
                authorService.createAuthor(userId, authorForm.getAuthorName());
        return ResponseEntity.ok(result);
    }
}
