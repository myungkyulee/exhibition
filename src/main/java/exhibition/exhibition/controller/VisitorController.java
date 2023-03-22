package exhibition.exhibition.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisitorController {
    @GetMapping("/")
    public String home(){
        return "this is home";
    }
}
