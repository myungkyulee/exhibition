package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.provider.JwtProvider;
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
    private final JwtProvider jwtProvider;

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

        visitor.setAuthor(author);
        authorRepository.save(author);
        String token = jwtProvider.generateToken(visitorId, visitor.getRoles());

        return CreateAuthor.Response.builder()
                .authorName(author.getName())
                .jwt(token)
                .roles(author.getVisitor().getRoles()).build();
    }
}
