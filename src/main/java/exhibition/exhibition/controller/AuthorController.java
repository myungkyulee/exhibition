package exhibition.exhibition.controller;


import exhibition.exhibition.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

}
