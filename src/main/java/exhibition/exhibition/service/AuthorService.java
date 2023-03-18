package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public CreateAuthor.Response join(CreateAuthor.Request request) {
        if (authorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("중복된 이메일 입니다.");
        }


        Author author = Author.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .build();

        return CreateAuthor.Response.from(authorRepository.save(author));
    }
}
