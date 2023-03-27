package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Follow;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.FollowRepository;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final VisitorRepository visitorRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public void follow(Long visitorId, String authorName) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_VISITOR));

        Author author = authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));

        Follow follow = Follow.builder()
                .visitor(visitor)
                .author(author)
                .build();

        followRepository.save(follow);
    }

    public int countFollow(Long authorId){
        return followRepository.countById(authorId);
    }

}
