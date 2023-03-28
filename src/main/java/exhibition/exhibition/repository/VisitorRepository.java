package exhibition.exhibition.repository;

import exhibition.exhibition.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findByEmail(String email);

    Optional<Visitor> findByVisitorName(String visitorName);

}
