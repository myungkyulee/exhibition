package exhibition.exhibition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

public class GetAuthorInfo {
    /*@NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {

    }*/


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private String authorName;
        private List<SeriesCover> seriesCovers;
        private int followNumber;

        //TODO: 팔로우 기능 구현하여 팔로우 수도 넣어야함
    }
}
