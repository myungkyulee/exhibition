package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Series;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.CreateAuthor;
import exhibition.exhibition.dto.GetAuthorInfo;
import exhibition.exhibition.dto.SeriesCover;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.SeriesRepository;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final VisitorRepository visitorRepository;
    private final JwtProvider jwtProvider;
    private final SeriesRepository seriesRepository;
    private final FollowService followService;

    @Transactional
    public CreateAuthor.Response createAuthor(Long visitorId, String authorName) {
        if (authorRepository.findByVisitorId(visitorId).isPresent()) {
            throw new ExhibitionException(ErrorCode.ALREADY_EXIST_AUTHOR);
        }

        if (authorRepository.existsByAuthorName(authorName)) {
            throw new ExhibitionException(ErrorCode.DUPLICATED_AUTHOR_NAME);
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_VISITOR));

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


    public GetAuthorInfo getAuthorInfo(String authorName, final Pageable pageable) {
        Author author = authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));

        Page<Series> seriesList = seriesRepository.findAllByAuthor(author, pageable);

        Page<SeriesCover> seriesCovers = seriesList.map(SeriesCover::from);

        int followNumber = followService.countFollows(author.getId());

        return GetAuthorInfo.builder()
                .authorName(author.getAuthorName())
                .seriesCovers(seriesCovers)
                .followNumber(followNumber)
                .build();
    }
}
