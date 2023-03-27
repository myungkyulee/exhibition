package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> findByIdAndAuthorId(Long workId, Long authorId);
}
