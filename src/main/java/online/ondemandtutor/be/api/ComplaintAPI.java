package online.ondemandtutor.be.api;

import online.ondemandtutor.be.entity.Complaint;
import online.ondemandtutor.be.model.ComplaintRequest;
import online.ondemandtutor.be.repository.ComplaintRepository;
import online.ondemandtutor.be.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintAPI {

    @Autowired
    ComplaintService complaintService;

    @PostMapping
    public ResponseEntity createComplaint(@RequestBody ComplaintRequest complaintRequest) {
        Complaint complaint = complaintService.createComplaint(complaintRequest);
        return ResponseEntity.ok(complaint);
        //ResponseEntity.ok() để tạo response với status 200 OK.
    }

}
