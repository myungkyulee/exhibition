package exhibition.exhibition.controller;


import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.provider.JwtProvider;
import exhibition.exhibition.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<CreateAuthor.Response> createAuthor(
            CreateAuthor.Request authorForm,
            HttpServletRequest request) {

        String token = jwtProvider.getToken(request);
        Long userId = jwtProvider.authenticate(token);

        CreateAuthor.Response result =
                authorService.createAuthor(userId, authorForm.getAuthorName());
        return ResponseEntity.ok(result);
    }
}
