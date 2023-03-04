package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("작가 생성 성공")
    void createAuthor() {
        // given
        CreateAuthor.Request request = new CreateAuthor.Request();
        request.setEmail("asd@naver.com");
        request.setName("홍길동");
        request.setPassword("홍길동password");

        given(authorRepository.save(any()))
                .willReturn(Author.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build());

        // when
        Author author = authorService.join(request);

        // then

        assertThat(author.getName()).isEqualTo(request.getName());
        assertThat(author.getEmail()).isEqualTo(request.getEmail());
        assertThat(author.getPassword()).isEqualTo(request.getPassword());
        //assertThat(author.getId()).isNotNull();
    }
}