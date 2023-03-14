package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.domain.Work;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;

    @Transactional
    public Work save(Long authorId, String title, String description, ImageFile image) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("없는 작가입니다."));

        return workRepository.save(
                Work.builder()
                        .title(title)
                        .description(description)
                        .author(author)
                        .image(image)
                        .build()
        );
    }
}
