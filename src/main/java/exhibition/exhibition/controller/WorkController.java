package exhibition.exhibition.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
public class WorkController {
    @Value("${image.dir}")
    private String imageDir;

    @GetMapping("/works/create")
    public String createWorkForm() {
        return "index";
    }

    @PostMapping("/works/create")
    @ResponseBody
    public String createWork(@RequestParam String title, @RequestParam String description, @RequestParam MultipartFile image) throws IOException {
        System.out.println(title);
        System.out.println(description);
        System.out.println(image);
        String authorName = "홍길동";
        if (!image.isEmpty()) {
            String fullPath = imageDir + image.getOriginalFilename();
            log.info("fullPath = {}", fullPath);
            image.transferTo(new File(fullPath));
        }

        return "ok";
    }
}