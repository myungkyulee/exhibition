package exhibition.exhibition.controller;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.dto.CreateUser;
import exhibition.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    @PostMapping("/signUp")
    public ResponseEntity<CreateUser.Response> signUp(
            @RequestBody @Valid CreateUser.Request request) {
        log.info("[UserController] signUp");
        return ResponseEntity.ok(userservice.join(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Authentication.Response> signIn(
            @RequestBody @Valid Authentication.Request request) {
        return ResponseEntity.ok(userservice.signIn(request));
    }
}
