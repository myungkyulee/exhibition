package exhibition.exhibition.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CreateAuthor {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank
        private String authorName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {
        private String authorName;
        private List<String> roles;
        private String jwt;
    }
}
