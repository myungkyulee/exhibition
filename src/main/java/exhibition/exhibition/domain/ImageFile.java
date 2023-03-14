package exhibition.exhibition.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class ImageFile {
    private String imageFileName;
    private String imageFileOriName;
    private String imageFileUrl;
}