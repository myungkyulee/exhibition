package exhibition.exhibition.controller;

import exhibition.exhibition.dto.GetAuthorInfo;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final JwtProvider jwtProvider;
    private final AuthorService authorService;

    @GetMapping("/{authorName}")
    public ResponseEntity<GetAuthorInfo.Response> getAuthorInfo(@PathVariable String authorName) {
        GetAuthorInfo.Response authorInfo = authorService.getAuthorInfo(authorName);
        return ResponseEntity.ok(authorInfo);
    }
}
