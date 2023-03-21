package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class CreateAuthor {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response {
        private String authorName;


        public static Response fromEntity(Author author) {
            return new Response(author.getName());
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String authorName;
    }
}
