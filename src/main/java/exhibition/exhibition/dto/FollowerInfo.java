package exhibition.exhibition.dto;

import exhibition.exhibition.domain.Visitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FollowerInfo {
    private String followerName;


    public static FollowerInfo from(Visitor visitor) {
        return FollowerInfo.builder()
                .followerName(visitor.getName())
                .build();
    }
}
