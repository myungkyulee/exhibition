package exhibition.exhibition.dto;

import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.domain.Work;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateWork {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String title;
        @Email
        private String description;
        @Min(8)
        @Max(16)
        private MultipartFile image;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String title;
        private String description;
        private ImageFile image;

        public static CreateWork.Response from(Work work) {
            return Response.builder()
                    .title(work.getTitle())
                    .description(work.getDescription())
                    .image(work.getImage()).build();
        }
    }
}
