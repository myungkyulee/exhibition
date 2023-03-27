package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Series;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.dto.GetAuthorInfo;
import exhibition.exhibition.dto.SeriesCover;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        if (authorRepository.existsByAuthorName(authorName)) {
            throw new RuntimeException("중복되는 작가명입니다.");
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        visitor.getRoles().add("AUTHOR");

        Author author = Author.builder()
                .authorName(authorName)
                .visitor(visitor)
                .build();

        visitor.setAuthor(author);
        authorRepository.save(author);
        String token = jwtProvider.generateToken(visitorId, visitor.getRoles());

        return CreateAuthor.Response.builder()
                .authorName(author.getAuthorName())
                .jwt(token)
                .roles(author.getVisitor().getRoles()).build();
    }

    public GetAuthorInfo.Response getAuthorInfo(String authorName) {
        Author author = authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));

        List<Series> seriesList = author.getSeriesList();

        List<SeriesCover> seriesCoverList = seriesList.stream()
                .map(s -> SeriesCover.builder()
                        .title(s.getTitle())
                        .imageURL(s.getCoverWork().getImage().getImageFileUrl())
                        .build())
                .collect(Collectors.toList());

        return GetAuthorInfo.Response.builder()
                .authorName(author.getAuthorName())
                .coverImageList(seriesCoverList)
                .build();
    }
}
