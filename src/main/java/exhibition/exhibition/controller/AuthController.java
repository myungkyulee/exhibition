package exhibition.exhibition.controller;

import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.dto.SignIn;
import exhibition.exhibition.dto.CreateVisitor;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.AuthorService;
import exhibition.exhibition.service.VisitorService;
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
    private final VisitorService visitorService;

    @PostMapping("/signUp")
    public ResponseEntity<CreateVisitor.Response> signUp(
            @RequestBody @Valid CreateVisitor.Request request) {

        return ResponseEntity.ok(visitorService.signUp(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignIn.Response> signIn(
            @RequestBody @Valid SignIn.Request request) {

        return ResponseEntity.ok(visitorService.signIn(request));
    }

    @PostMapping("/createAuthor")
    public ResponseEntity<CreateAuthor.Response> createAuthor(
            @RequestBody @Valid CreateAuthor.Request authorForm,
            @RequestHeader("Authorization") String token) {

        Long visitorId = jwtProvider.getAuthentication(token).getId();
        return ResponseEntity.ok(authorService.createAuthor(visitorId, authorForm.getAuthorName()));
    }
}
