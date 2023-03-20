package exhibition.exhibition.service;

import exhibition.exhibition.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
//    private final PasswordEncoder passwordEncoder;

    /*@Transactional
    public CreateAuthor.Response signUp(CreateAuthor.Request request) {
        if (authorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("중복된 이메일 입니다.");
        }

        Author author = Author.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return CreateAuthor.Response.fromEntity(authorRepository.save(author));
    }

    public Authentication.Response signIn() {
        return
    }*/
}
