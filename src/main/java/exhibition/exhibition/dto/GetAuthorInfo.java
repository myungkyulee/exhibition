package exhibition.exhibition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetAuthorInfo {
    private String authorName;
    private Page<SeriesCover> seriesCovers;
    private int followNumber;
}
