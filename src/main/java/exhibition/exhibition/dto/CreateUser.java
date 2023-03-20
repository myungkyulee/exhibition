package exhibition.exhibition.dto;

import exhibition.exhibition.domain.User;
import lombok.*;

import javax.validation.constraints.*;

public class CreateUser {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        @NotBlank
        private String name;
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

        public static CreateUser.Response fromEntity(User user) {
            return Response.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }
}
