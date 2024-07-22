package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Complaint;
import online.ondemandtutor.be.enums.ComplaintEnum;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.EmailDetail;
import online.ondemandtutor.be.model.request.*;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private EmailService emailService;

    // neu STUDENT complaint ve 1 TUTOR qua 3 lan thi se khong duoc complaint nua
    public Complaint createComplaint(ComplaintRequest request) {
        Account sender = authenticationService.getCurrentAccount();

        if(request.getTutorEmail() == null){
            throw new BadRequestException("Tutor email must not be empty!");
        }
        if (request.getContent() == null){
            throw new BadRequestException("Content must not be empty!");
        }

        long count = complaintRepository.countByFromStudentIdAndTutorEmailAndStatus(sender.getId(), request.getTutorEmail(), ComplaintEnum.APPROVED);
        if (count >= 3) {
            throw new BadRequestException("You have reached the maximum complaints for this tutor");
        }

        Complaint newComplaint = new Complaint();
        newComplaint.setContent(request.getContent());
        newComplaint.setFromStudent(sender);
        newComplaint.setTutorEmail(request.getTutorEmail());
        newComplaint.setStatus(ComplaintEnum.PENDING);

        sendComplaintToModerator(sender);
        return complaintRepository.save(newComplaint);
    }

    public void sendComplaintToModerator(Account student){
        List<Account> listMod = authenticationRepository.findAccountByRole(RoleEnum.MODERATOR);
        for (Account mod : listMod) {
            //copy tu ForgetPassword
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(mod.getEmail());
            emailDetail.setSubject("Complaint from account " + student.getEmail() + "!");
            emailDetail.setMsgBody("Hello sexy complaint!");
            emailDetail.setButtonValue("View complaint");
            emailDetail.setFullName(mod.getFullname());
            // chờ FE gửi link dashboard up role
            emailDetail.setLink("http://localhost:5173");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        }
    }

    //chuc nang duoi la` cho MOD bam duyet APPROVED trong DASHBOARD
    public Complaint ApprovedComplaint(long id){

        Complaint complaint = complaintRepository.findComplaintById(id);
        Account sender = authenticationRepository.findAccountById(complaint.getFromStudent().getId());

        if(complaint != null){
            complaint.setStatus(ComplaintEnum.APPROVED);
            SendComplaintStatusToStudent(sender, "APPROVED!");
            return complaintRepository.save(complaint);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    //chuc nang duoi la` cho MOD bam duyet rejected trong DASHBOARD
    public Complaint RejectedComplaint(long id){

        Complaint complaint = complaintRepository.findComplaintById(id);
        Account sender = authenticationRepository.findAccountById(complaint.getId());

        if(complaint != null){
            complaint.setStatus(ComplaintEnum.REJECTED);
            SendComplaintStatusToStudent(sender, "REJECTED!");
            return complaintRepository.save(complaint);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    // he thong gui mail thong bao status Complaint Request cho STUDENT sau khi MOD thao tac
    public void SendComplaintStatusToStudent(Account account, String msg){
        //copy tu ForgetPassword
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Response from MODERATOR for " + account.getEmail() + " complaint request!");
        emailDetail.setMsgBody(msg);
        // chờ FE gửi web chính thức
        emailDetail.setButtonValue("Check your complaint!");
        emailDetail.setFullName(account.getFullname());
        // chờ FE gửi link trang web
        emailDetail.setLink("http://ondemandtutor.online");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    // lay danh sach tat ca nhung user dang yeu cau Up Role
    public List<Complaint> getAllAccountsHavePendingComplaint(){
        return complaintRepository.findByStatus(ComplaintEnum.PENDING);
    }

    public List<Complaint> getAllComplaints(){
        return complaintRepository.findAll();
    }
}
