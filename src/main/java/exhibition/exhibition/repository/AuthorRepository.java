package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByVisitorId(Long userId);
    boolean existsByAuthorName(String authorName);

    Optional<Author> findByAuthorName(String authorName);

    Page<Author> findAllByAuthorNameStartingWithIgnoreCase(String keyword, Pageable pageable);
}
