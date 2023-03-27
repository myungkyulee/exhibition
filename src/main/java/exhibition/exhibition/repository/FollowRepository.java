package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    int countById(Long authorId);
}
