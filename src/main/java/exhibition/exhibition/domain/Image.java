package exhibition.exhibition.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Image {

    private String fileName;
    private String fileOriName;

    private String fileUrl;
}
