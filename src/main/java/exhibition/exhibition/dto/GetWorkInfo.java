package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class GetWorkInfo {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private Long workId;
        private String title;
        private String description;
        private String imageFileURL;

        public static Response fromEntity(Work work) {
            return Response.builder()
                    .workId(work.getId())
                    .title(work.getTitle())
                    .description(work.getDescription())
                    .imageFileURL("/images/" + work.getImage().getImageFileName())
                    .build();
        }
    }
}
