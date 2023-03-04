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
    public Author join(CreateAuthor.Request authorJoinForm) {
        if (authorRepository.findByEmail(authorJoinForm.getEmail()).isPresent()) {
            throw new RuntimeException("중복된 이메일 입니다.");
        }

        Author author = Author.builder()
                .email(authorJoinForm.getEmail())
                .name(authorJoinForm.getName())
                .password(authorJoinForm.getPassword())
                .build();

        Author returnAuthor =  authorRepository.save(author);
        return returnAuthor;
    }
}
