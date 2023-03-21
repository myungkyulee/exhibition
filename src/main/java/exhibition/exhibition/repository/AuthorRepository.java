package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUserId(Long userId);

    boolean existsByName(String authorName);
}
