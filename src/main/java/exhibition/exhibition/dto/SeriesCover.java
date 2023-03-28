package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Series;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SeriesCover {
    private String imageURL;
    private String title;


    public static SeriesCover from(Series series) {
        return SeriesCover.builder()
                .title(series.getTitle())
                .imageURL(series.getCoverWork().getImage().getImageFileUrl())
                .build();
    }
}
