package exhibition.exhibition.controller;

import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.dto.CreateWork;
import exhibition.exhibition.dto.GetWorkInfo;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.repository.ImageFileStore;
import exhibition.exhibition.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final ImageFileStore imageFileStore;
    private final JwtProvider jwtProvider;

    @GetMapping("/works/{workId}")
    public ResponseEntity<GetWorkInfo.Response> getWorkInfo(@PathVariable Long workId) {
        GetWorkInfo.Response workInfo = workService.getWorkInfo(workId);
        return ResponseEntity.ok(workInfo);
    }

    @PostMapping("/works")
    public ResponseEntity<CreateWork.Response> createWork(
            @RequestParam @NotBlank String title,
            @RequestParam @NotBlank String description,
            @RequestParam("image") @NotBlank MultipartFile imageFile,
            @RequestHeader("Authorization") String token) throws IOException {

        Long visitorId = jwtProvider.getAuthentication(token).getId();

        ImageFile image = imageFileStore.storeImageFile(imageFile);
        CreateWork.Response work = workService.createWork(visitorId, title, description, image);

        return ResponseEntity.ok(work);
    }

    @GetMapping("/images/{imageFileName}")
    public Resource showImage(@PathVariable String imageFileName) throws MalformedURLException {
        return new UrlResource("file:" + imageFileStore.getFullPath(imageFileName));
    }
}