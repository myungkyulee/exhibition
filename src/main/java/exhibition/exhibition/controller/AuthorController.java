package exhibition.exhibition.controller;

import exhibition.exhibition.dto.GetAuthorInfo;
import exhibition.exhibition.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{authorName}")
    public ResponseEntity<GetAuthorInfo> getAuthorInfo(
            @PathVariable String authorName,
            final Pageable pageable) {
        GetAuthorInfo authorInfo = authorService.getAuthorInfo(authorName, pageable);
        return ResponseEntity.ok(authorInfo);
    }
}
