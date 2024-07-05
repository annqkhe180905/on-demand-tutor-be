package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Complaint;
import online.ondemandtutor.be.model.ComplaintRequest;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.repository.ComplaintRepository;
import online.ondemandtutor.be.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    public Complaint createComplaint(ComplaintRequest complaintRequest) {
        // tìm account theo email của nguoiBiPhanNan
        Account nguoiBiPhanNan = authenticationRepository.findAccountByEmail(complaintRequest.getNguoiBiPhanNan());

        if (nguoiBiPhanNan == null) {
            throw new BadRequestException("Account not found!!");
        }

        // check số lần complaints của nguoiPhanNan
        long complaintCount = complaintRepository.countByNguoiPhanNan(complaintRequest.getNguoiPhanNan());
        if (complaintCount >= 3) {
            throw new BadRequestException("You have reached the maximum number of complaints!");
        }

        // new complaint
        Complaint complaint = new Complaint();
        complaint.setContent(complaintRequest.getContent());
        complaint.setReceiverId(complaintRequest.getReveiverId());
        complaint.setNguoiPhanNan(complaintRequest.getNguoiPhanNan());
        complaint.setNguoiBiPhanNan(complaintRequest.getNguoiBiPhanNan());
        complaint.setCreatedAt(new Date());

        // save complaint to db
        return complaintRepository.save(complaint);
    }
}
