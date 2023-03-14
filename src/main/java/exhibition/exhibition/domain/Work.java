package exhibition.exhibition.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Work {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;
    private String description;

    @Embedded
    @Column(nullable = false)
    private ImageFile image;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}