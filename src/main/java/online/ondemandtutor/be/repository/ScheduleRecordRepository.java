package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {
    ScheduleRecord findByWeekDayIdAndTeachingSlotId(Long weekDayIds, Long teachingSlotIds);
}
