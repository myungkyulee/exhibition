package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final VisitorRepository visitorRepository;

    @Transactional
    public CreateAuthor.Response createAuthor(Long visitorId, String authorName) {
        if (authorRepository.findByVisitorId(visitorId).isPresent()) {
            throw new RuntimeException("이미 author가 존재합니다.");
        }

        if (authorRepository.existsByName(authorName)) {
            throw new RuntimeException("중복되는 작가명입니다.");
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        visitor.getRoles().add("AUTHOR");

        Author author = Author.builder()
                .name(authorName)
                .visitor(visitor)
                .build();

        return CreateAuthor.Response.fromEntity(author);
    }
}
