package exhibition.exhibition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    @Getter
    @Setter
    @AllArgsConstructor
    //@NoArgsConstructor
    public static class Response{

    }
}
