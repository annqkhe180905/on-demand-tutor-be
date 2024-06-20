package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.TutorCertificate;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.*;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;

    //up Role cho registered user -> phai thong qua MOD duyet
    //  bao gom certificate URL -> gui den mail cho MOD -> APPROVED OR REJECTED

    // STUDENT GUI REQUEST KEM` CERTIFICATE
    public Account UpRole(UpRoleRequest upRoleRequest){
        Account account = authenticationService.getCurrentAccount();
        TutorCertificate certificate = new TutorCertificate();
        if(account != null){
            certificate.setUrl(upRoleRequest.getCertificateUrl());
            account.setRequestStatus(StatusEnum.PENDING);
            SendUpRoleRegistrationToModerator(account);
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    //chuc nang duoi la` cho MOD bam duyet APPROVED trong DASHBOARD
    public Account ApprovedUpRole(UpRoleRequestByAccountId id){

        Account account = authenticationRepository.findAccountById(id.getAccountId());

        if(account != null){
            account.setRole(RoleEnum.TUTOR);
            account.setRequestStatus(StatusEnum.APPROVED);
            SendUpRoleRequestStatusToStudent(account, "APPROVED!");
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    //chuc nang duoi la` cho MOD bam duyet rejected trong DASHBOARD
    public Account RejectedUpRole(UpRoleRequestByAccountId id){

        Account account = authenticationRepository.findAccountById(id.getAccountId());
        if(account != null){
            account.setRequestStatus(StatusEnum.REJECTED);
            SendUpRoleRequestStatusToStudent(account, "REJECTED!");

            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    public void SendUpRoleRegistrationToModerator(Account student){
        List<Account> listMod = authenticationRepository.findAccountByRole(RoleEnum.MODERATOR);
        for (Account mod : listMod) {
            //copy tu ForgetPassword
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(mod.getEmail());
            emailDetail.setSubject("Upgrade Tutor role for account " + student.getEmail() + "!");
            emailDetail.setMsgBody("");
            emailDetail.setButtonValue("Upgrade to Tutor");
            emailDetail.setFullName(mod.getFullname());
            // chờ FE gửi link dashboard up role
            emailDetail.setLink("http://localhost:5173/account");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();
        }

    }
    // he thong gui mail thong bao status Up Role Request cho STUDENT sau khi MOD thao tac
    public void SendUpRoleRequestStatusToStudent(Account account, String msg){

        //copy tu ForgetPassword
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Response from MODERATOR for " + account.getEmail() + " up role request!");
        emailDetail.setMsgBody(msg);
        // chờ FE gửi web chính thức
        emailDetail.setButtonValue("Welcome new Tutor!");
        emailDetail.setFullName(account.getFullname());
        // chờ FE gửi link trang web
        emailDetail.setLink("http://ondemandtutor.online/login");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }

    // lay danh sach tat ca nhung user dang yeu cau Up Role
    public List<Account> getAllAccountsHaveUpRoleRequest(){
        return authenticationRepository.findAccountByRequestStatus(StatusEnum.PENDING);

    }

    ////////////////////////////



    ////////////////////////////

    //lay danh sach tat ca user, bat ke isDeleted
    public List<Account> getAllAccounts(){
        return authenticationRepository.findAll();
    }

    //lay user bang id, bat ke isDeleted
    public Account getAccountById(Long id){
        Account account = authenticationRepository.findAccountById(id);
        if(account == null){
            throw new BadRequestException("Account is not found!");
        }
        return account;
    }

    public Account updateAccount(UpdateRequest updateRequest) {
        Account account = authenticationService.getCurrentAccount();
        account.setPhone(updateRequest.getPhone());
        account.setFullname(updateRequest.getFullname());

        Account newAccount = authenticationRepository.save(account);
        return newAccount;
    }

    public Account changeStatusIsDeletedByAdmin (Long id){
        Account account = authenticationRepository.findAccountByIdAndIsDeletedFalse(id);
        if(account != null){
            account.setDeleted(true);
            return authenticationRepository.save(account);
        }
        else {
            throw new BadRequestException("Account is not found!");
        }
    }

    public Account changeStatusIsNotDeletedByAdmin (Long id){
        Account account = authenticationRepository.findAccountByIdAndIsDeletedTrue(id);
        if(account != null){
            account.setDeleted(false);
            return authenticationRepository.save(account);
        }
        else {
            throw new BadRequestException("Account is not found!");
        }
    }

    //lay danh sach tat ca user bi xoa
    public List<Account> getAllAccountsIsDeleted() {
        return authenticationRepository.findAccountByIsDeletedTrue();
    }
    ////lay danh sach tat ca user bi xoa
    public List<Account> getAllAccountsIsNotDeleted() {
        return authenticationRepository.findAccountByIsDeletedFalse();
    }
}
