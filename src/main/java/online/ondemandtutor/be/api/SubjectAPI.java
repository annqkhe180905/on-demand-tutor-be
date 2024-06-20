package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Subject;
import online.ondemandtutor.be.model.SubjectRequest;
import online.ondemandtutor.be.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")

public class SubjectAPI {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/subject")
    public ResponseEntity<List<Subject>> getSubjects() {
        List<Subject> printAll = subjectService.findAll();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/subject/{name}")
    public ResponseEntity<List<Subject>> getSubjectByName(@PathVariable String name) {
        List<Subject> printAll = subjectService.findByName(name);
        return ResponseEntity.ok(printAll);
    }

    @PostMapping("/subject")
    @PreAuthorize("hasAuthority('TUTOR')")
    public ResponseEntity createSubject(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = subjectService.createSubject(subjectRequest);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/subject/{id}")
    @PreAuthorize("hasAuthority('TUTOR')")
    public ResponseEntity updateSubject(@RequestBody SubjectRequest subjectRequest, @PathVariable long id) {
        Subject subject = subjectService.updateSubject(subjectRequest, id);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/subject/{id}")
    @PreAuthorize("hasAuthority('TUTOR')")
    public ResponseEntity deleteSubject(@PathVariable long id) {
        Subject subject = subjectService.deleteSubject(id);
        return ResponseEntity.ok(subject);
    }
}
