package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateSeries {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request{
        private Long coverWorkId;
        @NotBlank
        private String title;
        @NotEmpty
        private String description;
        private List<Long> workIdList;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long seriesId;
        private String title;
        private String description;

        public static Response fromEntity(Series series) {
            return Response.builder()
                    .title(series.getTitle())
                    .seriesId(series.getId())
                    .description(series.getDescription())
                    .build();
        }
    }
}
