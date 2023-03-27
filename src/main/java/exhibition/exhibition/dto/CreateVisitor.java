package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Visitor;
import lombok.*;

import javax.validation.constraints.*;

public class CreateVisitor {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        @NotBlank
        private String name;
        @NotBlank
        private String visitorName;
        @Email
        private String email;
        @Size(max = 15, min = 8)
        private String password;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private String email;

        public static CreateVisitor.Response fromEntity(Visitor visitor) {
            return Response.builder()
                    .name(visitor.getName())
                    .email(visitor.getEmail())
                    .build();
        }
    }
}
