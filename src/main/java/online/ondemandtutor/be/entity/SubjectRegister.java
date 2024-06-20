package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SubjectRegister {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "subjectRegister")
    EducationLevel educationLevelId;

    @OneToMany(mappedBy = "subjectRegister")
    List<Location> locationIds;

    @OneToMany(mappedBy = "subjectRegister")
    List<Subject> subjectIds;

    @OneToMany(mappedBy = "subjectRegister")
    List<Grade> gradeIds;

    @OneToOne(mappedBy = "subjectRegister")
    TutorVideo TutorVideoUrl;

    @OneToMany(mappedBy = "subjectRegister")
    List<WeekDay> weekDayIds;

    @OneToMany(mappedBy = "subjectRegister")
    List<TeachingSlot> teachingSlotIds;

    @OneToOne(mappedBy = "subjectRegister")
    Account accountId;

    String brief;

}
