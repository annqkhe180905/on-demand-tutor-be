package online.ondemandtutor.be.api;

import online.ondemandtutor.be.entity.SearchTutor;
import online.ondemandtutor.be.model.SearchTutorRequest;
import online.ondemandtutor.be.service.SearchTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/searchTutors")
public class SearchTutorAPI {

    @Autowired
    SearchTutorService searchTutorService;

    @PostMapping("/byName")
    public ResponseEntity<List<SearchTutor>> searchTutorsByName(@RequestBody SearchTutorRequest searchTutorRequest) {
        List<SearchTutor> tutors = searchTutorService.searchTutorsByName(searchTutorRequest);
        return ResponseEntity.ok(tutors);
    }

    @PostMapping("/bySubject")
    public ResponseEntity<List<SearchTutor>> searchTutorsBySubject(@RequestBody SearchTutorRequest searchTutorRequest) {
        List<SearchTutor> tutors = searchTutorService.searchTutorsBySubject(searchTutorRequest);
        return ResponseEntity.ok(tutors);
    }

    @PostMapping("/byLocation")
    public ResponseEntity<List<SearchTutor>> searchTutorsByLocation(@RequestBody SearchTutorRequest searchTutorRequest) {
        List<SearchTutor> tutors = searchTutorService.searchTutorsByLocation(searchTutorRequest);
        return ResponseEntity.ok(tutors);
    }
}
