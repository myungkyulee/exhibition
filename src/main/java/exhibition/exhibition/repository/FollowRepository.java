package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Follow;
import exhibition.exhibition.domain.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    int countById(Long authorId);

    Optional<Follow> findByVisitorIdAndAuthorId(Long visitor, Long author);

    Page<Follow> findAllByVisitorId(Long id, Pageable pageable);

    Page<Follow> findAllByAuthorId(Long id, Pageable pageable);
}
