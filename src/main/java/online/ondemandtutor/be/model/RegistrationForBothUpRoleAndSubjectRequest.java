package online.ondemandtutor.be.model;

import lombok.Data;

import java.util.List;

@Data
public class RegistrationForBothUpRoleAndSubjectRequest {
    Long educationLevelId;
    List<Long> locationIds;
    List<Long> subjectIds;
    String TutorVideoUrl;
    String brief;
//    TutorSchedule schedule;
}
