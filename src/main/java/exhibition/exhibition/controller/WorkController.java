package exhibition.exhibition.controller;

import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.domain.Work;
import exhibition.exhibition.repository.ImageFileStore;
import exhibition.exhibition.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final ImageFileStore imageFileStore;


    @GetMapping("/works/create")
    public String createWorkForm() {
        return "index";
    }

    @PostMapping("/works/create")
    public String createWork(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam("image") MultipartFile imageFile,
                             Model model) throws IOException {
        ImageFile image = imageFileStore.storeImageFile(imageFile);
        Long authorId = 1L;

        Work work = workService.save(authorId, title, description, image);

        model.addAttribute("work", work);

        return "showImage";
    }

    @GetMapping("/images/{imageFileName}")
    @ResponseBody
    public Resource showImage(@PathVariable String imageFileName) throws MalformedURLException {
        return new UrlResource("file:" + imageFileStore.getFullPath(imageFileName));
    }

}