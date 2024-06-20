package online.ondemandtutor.be.model;

import lombok.Data;
import online.ondemandtutor.be.entity.Subject;

import java.util.List;

@Data
public class SubjectRegisterRequest {
    Long educationLevelId;
    List<Long> locationIds;
    List<Long> subjectIds;
    List<Long> gradeIds;
    String TutorVideoUrl;
    String brief;
}
