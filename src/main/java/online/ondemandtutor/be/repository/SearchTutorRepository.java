package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.SearchTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchTutorRepository extends JpaRepository<SearchTutor, Long> {
    List<SearchTutor> findByNameContainingIgnoreCase(String name);
}
