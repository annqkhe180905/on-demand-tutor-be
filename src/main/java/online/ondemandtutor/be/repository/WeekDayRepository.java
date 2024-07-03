package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface WeekDayRepository extends JpaRepository<WeekDay, Long> {
    public List<WeekDay> findAll();
    public WeekDay findWeekDayById(Long id);

}
