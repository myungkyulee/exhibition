package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.User;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateAuthor.Response createAuthor(Long userId, String authorName) {
        if (authorRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("이미 author가 존재합니다.");
        }

        if (authorRepository.existsByName(authorName)) {
            throw new RuntimeException("중복되는 작가명입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        Author author = Author.builder()
                .name(authorName)
                .user(user)
                .build();

        return CreateAuthor.Response.fromEntity(author);
    }
}
