package exhibition.exhibition.repository;

import exhibition.exhibition.domain.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ImageFileStore {
    @Value("${image.dir}")
    private String imageDir;

    public String getFullPath(String storeName){
        return imageDir + storeName;
    }

    public List<ImageFile> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ImageFile> list = new ArrayList<>();

        for (MultipartFile m : multipartFiles) {
            list.add(storeImageFile(m));
        }
        return list;
    }


    public ImageFile storeImageFile(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return null;
        }

        String imageOriName = imageFile.getOriginalFilename();
        String storeName = createStoreImageName(imageOriName);

        log.info("imageOriName = {}", imageOriName);

        imageFile.transferTo(new File(getFullPath(storeName)));

        return new ImageFile(storeName, imageOriName, imageDir.substring(2) + storeName);
    }

    private String createStoreImageName(String originalImageName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalImageName);
        return uuid + ext;
    }

    private String extractExt(String originalImageName) {
        int idx = originalImageName.lastIndexOf(".");
        return originalImageName.substring(idx);
    }

}
