package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Follow;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.FollowerInfo;
import exhibition.exhibition.dto.FollowingInfo;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.FollowRepository;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    @Transactional
    public void unfollow(Long visitorId, String authorName) {
        if (!visitorRepository.existsById(visitorId)) {
            throw new ExhibitionException(ErrorCode.NOT_FOUND_VISITOR);
        }

        Author author = authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));

        if (Objects.equals(author.getVisitor().getId(), visitorId)) {
            throw new ExhibitionException(ErrorCode.FOLLOW_SAME_FOLLOWING);
        }

        Follow follow = followRepository.findByVisitorIdAndAuthorId(visitorId, author.getId())
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_FOLLOW));

        followRepository.delete(follow);
    }


    public Page<FollowerInfo> getFollowers(String authorName, final Pageable pageable) {
        Author author = authorRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));

        Page<Follow> follows = followRepository.findAllByAuthorId(author.getId(), pageable);

        return follows.map(f -> FollowerInfo.from(f.getVisitor()));
    }

    public Page<FollowingInfo> getFollowings(String visitorName, Pageable pageable) {
        Visitor visitor = visitorRepository.findByVisitorName(visitorName)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_VISITOR));

        Page<Follow> follows = followRepository.findAllByVisitorId(visitor.getId(), pageable);

        return follows.map(f -> FollowingInfo.from(f.getAuthor()));
    }
}
