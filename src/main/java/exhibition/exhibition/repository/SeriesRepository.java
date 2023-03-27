package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findAllByAuthorId(Long id);
}
