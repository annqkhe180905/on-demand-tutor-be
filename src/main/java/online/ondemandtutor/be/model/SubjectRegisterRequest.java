package online.ondemandtutor.be.model;

import lombok.Data;

import java.util.List;

@Data
public class SubjectRegisterRequest {
    Long educationLevelId;
    List<Long> locationIds;
    List<Long> subjectIds;
    List<Long> gradeIds;
    String TutorVideoUrl;
    List<Long> weekDayIds;
    List<Long> teachingSlotIds;
    Long accountId;
    String brief;
}
