package online.ondemandtutor.be.api;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Subject;
import online.ondemandtutor.be.model.SubjectRegisterRequest;
import online.ondemandtutor.be.model.UpRoleRequestByAccountId;
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

//    @PostMapping("/subject")
//    @PreAuthorize("hasAuthority('TUTOR')")
//    public ResponseEntity createSubject(@RequestBody SubjectRequest subjectRequest) {
//        Subject subject = subjectService.createSubject(subjectRequest);
//        return ResponseEntity.ok(subject);
//    }
//
//    @PutMapping("/subject/{id}")
//    @PreAuthorize("hasAuthority('TUTOR')")
//    public ResponseEntity updateSubject(@RequestBody SubjectRequest subjectRequest, @PathVariable long id) {
//        Subject subject = subjectService.updateSubject(subjectRequest, id);
//        return ResponseEntity.ok(subject);
//    }

//    @DeleteMapping("/subject/{id}")
//    @PreAuthorize("hasAuthority('TUTOR')")
//    public ResponseEntity deleteSubject(@PathVariable long id) {
//        Subject subject = subjectService.deleteSubject(id);
//        return ResponseEntity.ok(subject);
//    }

    @PostMapping("/subject/register-for-tutor")
    public void registerForTutor(@RequestBody SubjectRegisterRequest request) {
         subjectService.subjectRegister(request);
    }

    @PostMapping("/approved-subject-registration")
    @PreAuthorize("hasAuthority('MODERATOR')")

    public ResponseEntity approvedSubjectRegistration(@RequestBody UpRoleRequestByAccountId id){
        return ResponseEntity.ok(subjectService.ApprovedSubject(id));
    }

    @PostMapping("/rejected-subject-registration")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public void rejectedSubjectRegistration(@RequestBody UpRoleRequestByAccountId id) {
        subjectService.RejectedSubject(id);
    }

    @GetMapping("/pending-registration")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<List<Account>> getAccountHasRequest (){
        List<Account> printAll = subjectService.getAllAccountsHaveSubjectRegistrationRequest();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/approved-registration")
    public ResponseEntity<List<Account>> getApprovedAccount (){
        List<Account> printAll = subjectService.getAllAccountsHaveApprovedSubjectRegistrationRequest();
        return ResponseEntity.ok(printAll);
    }

    @PutMapping("/subject/register-for-tutor")
    public ResponseEntity updateSubjectRegister(@RequestBody SubjectRegisterRequest request) {
        return ResponseEntity.ok(subjectService.updateSubjectRegister(request));
    }


}
