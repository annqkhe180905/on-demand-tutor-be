package online.ondemandtutor.be.model;

import lombok.Data;

@Data
public class TutorScheduleRequest {
    private long id;
    String weekDay;
    String teachingTime;
    long account_id;
    long subject_id;
    long booking_id;
}
