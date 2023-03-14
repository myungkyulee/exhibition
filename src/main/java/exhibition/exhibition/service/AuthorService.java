package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public Author join(String name, String email, String password) {
        if (authorRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("중복된 이메일 입니다.");
        }

        Author author = Author.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        return authorRepository.save(author);
    }
}
