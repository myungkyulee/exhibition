package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Author;
import lombok.*;

import javax.validation.constraints.*;

public class CreateAuthor {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        @NotBlank
        private String name;
        @Email
        private String email;
        @Min(8)
        @Max(16)
        private String password;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private String email;

        public static CreateAuthor.Response from(Author author) {
            return Response.builder()
                    .name(author.getName())
                    .email(author.getEmail())
                    .build();
        }
    }
}
