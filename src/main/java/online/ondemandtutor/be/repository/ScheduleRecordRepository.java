package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {
//    ScheduleRecord findByWeekDayIdAndTeachingSlotId(Long weekDayIds, Long teachingSlotIds);

    List<ScheduleRecord> findAllByAccount(Account account);

//    @Query(value = "select * from schedule_record where teaching_slot_id = :teachingSlotId and week_day_id = :weekDayId", nativeQuery = true)
//    ScheduleRecord findByWeekDayIdAndTeachingSlotId(@Param("teachingSlotId") Long teachingSlotId, @Param("weekDayId") Long weekDayId);
    ScheduleRecord findByWeekDayIdAndTeachingSlotId(Long weekDayId, Long teachingSlotId);
}
