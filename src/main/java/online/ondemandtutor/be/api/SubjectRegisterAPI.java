package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class SubjectRegisterAPI {
//    @PostMapping
//    public ResponseEntity createSubjectRegistrationForm(@RequestBody Subject subject) {
//        ádadsada
//    }

}
