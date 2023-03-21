package exhibition.exhibition.controller;

import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.dto.CreateWork;
import exhibition.exhibition.provider.JwtProvider;
import exhibition.exhibition.repository.ImageFileStore;
import exhibition.exhibition.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final ImageFileStore imageFileStore;
    private final JwtProvider jwtProvider;

    @GetMapping("/works/create")
    public String createWorkForm() {
        return "index";
    }

    @PostMapping("/works/create")
    public ResponseEntity<CreateWork.Response> createWork(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("image") MultipartFile imageFile,
            HttpServletRequest request) throws IOException {

        String token = jwtProvider.getToken(request);
        Long userId = jwtProvider.authenticate(token);

        ImageFile image = imageFileStore.storeImageFile(imageFile);
        CreateWork.Response work = workService.createWork(userId, title, description, image);

        return ResponseEntity.ok(work);
    }

    @GetMapping("/images/{imageFileName}")
    public Resource showImage(@PathVariable String imageFileName) throws MalformedURLException {
        return new UrlResource("file:" + imageFileStore.getFullPath(imageFileName));
    }
}