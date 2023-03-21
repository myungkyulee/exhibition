package exhibition.exhibition.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class Authentication {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @Email
        private String email;

        @Size(max = 15, min = 8)
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String username;
        private String jwt;
    }
}
