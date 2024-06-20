package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.TutorSchedule;
import online.ondemandtutor.be.model.TutorScheduleRequest;
import online.ondemandtutor.be.repository.TutorScheduleRepository;
import online.ondemandtutor.be.service.TutorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedule")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('TUTOR')")
public class TutorScheduleAPI {
    @Autowired
    TutorScheduleService tutorScheduleService;
    @Autowired
    private TutorScheduleRepository tutorScheduleRepository;

    @PostMapping
    public ResponseEntity addTutorSchedule(@RequestBody TutorScheduleRequest tutorScheduleRequest) {
        TutorSchedule schedule = tutorScheduleService.addSchedule(tutorScheduleRequest);
        return  ResponseEntity.ok(schedule);
    }

    @PutMapping("{id}")
    public ResponseEntity updateTutorSchedule(@PathVariable long id, @RequestBody TutorScheduleRequest tutorScheduleRequest) {
        TutorSchedule schedule = tutorScheduleService.updateSchedule(tutorScheduleRequest, id);
        return  ResponseEntity.ok(schedule);
    }

    @GetMapping("{id}")
    public ResponseEntity findTutorSchedule(@PathVariable long id) {
        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(id);
        return  ResponseEntity.ok(schedule);
    }
}
