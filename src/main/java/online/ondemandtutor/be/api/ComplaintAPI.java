package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Complaint;
import online.ondemandtutor.be.model.request.ComplaintRequest;
import online.ondemandtutor.be.model.request.ComplaintRequestByAccountId;
import online.ondemandtutor.be.repository.ComplaintRepository;
import online.ondemandtutor.be.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class ComplaintAPI {
    @Autowired
    ComplaintService complaintService;
    @Autowired
    ComplaintRepository complaintRepository;

    @PostMapping("/complaint")
    public ResponseEntity createComplaint(@RequestBody ComplaintRequest request){
        Complaint complaint = complaintService.createComplaint(request);
        return ResponseEntity.ok(complaint);
    }

    @PostMapping("/complaint/approved/{id}")
    public ResponseEntity ApprovedComplaint(@PathVariable long id){
        Complaint complaint = complaintService.ApprovedComplaint(id);
        return ResponseEntity.ok(complaint);
    }

    @PostMapping("/complaint/rejected/{id}")
    public ResponseEntity RejectedComplaint(@PathVariable long id){
        Complaint complaint = complaintService.RejectedComplaint(id);
        return ResponseEntity.ok(complaint);
    }

    @GetMapping("/pending-complaint")
    public ResponseEntity<List<Complaint>> getAllAccountsHavePendingComplaint(){
        List<Complaint> complaints = complaintService.getAllAccountsHavePendingComplaint();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaint(){
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

}
