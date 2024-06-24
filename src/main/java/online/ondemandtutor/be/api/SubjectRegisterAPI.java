package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.service.SubjectRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class SubjectRegisterAPI {

    @Autowired
    private SubjectRegisterService subjectRegisterService;

    @GetMapping("education-level")
    public ResponseEntity<List<EducationLevel>> getAllEducationLevel() {
        List<EducationLevel> eduLv = subjectRegisterService.listOfEducationLevels();
        return ResponseEntity.ok(eduLv);
    }

    @GetMapping("location")
    public ResponseEntity<List<Location>> getAllLocation() {
        List<Location> location = subjectRegisterService.listOfLocations();
        return ResponseEntity.ok(location);
    }

    @GetMapping("grade")
    public ResponseEntity<List<Grade>> getAllGrade() {
        List<Grade> grade = subjectRegisterService.listOfGrades();
        return ResponseEntity.ok(grade);
    }

    @GetMapping("week-days")
    public ResponseEntity<List<WeekDay>> getAllWeekdays() {
        List<WeekDay> days = subjectRegisterService.listOfWeekDays();
        return ResponseEntity.ok(days);
    }

    @GetMapping("teaching-slots")
    public ResponseEntity<List<TeachingSlot>> getAllTeachingSlots() {
        List<TeachingSlot> slots = subjectRegisterService.listOfTeachingSlots();
        return ResponseEntity.ok(slots);
    }
}
