package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowingInfo {
    private String followingName;

    public static FollowingInfo from(Author author) {
        return FollowingInfo.builder()
                .followingName(author.getAuthorName()).build();
    }
}
