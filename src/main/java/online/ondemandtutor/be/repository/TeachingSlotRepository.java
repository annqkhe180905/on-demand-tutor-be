package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.TeachingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingSlotRepository extends JpaRepository<TeachingSlot, Long> {
    public List<TeachingSlot> findAll();
    public TeachingSlot findTeachingSlotById(Long id);
}
