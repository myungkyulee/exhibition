package exhibition.exhibition.controller;


import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

 /*   @PostMapping
    public ResponseEntity<CreateAuthor.Response> createAuthor(CreateAuthor.Request request){
        return ResponseEntity.ok(authorService.signUp(request));
    }*/

}
