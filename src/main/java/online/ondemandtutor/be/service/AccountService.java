package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.TutorCertificate;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.enums.MonthlyPackageEnum;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.enums.TransactionEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.EmailDetail;
import online.ondemandtutor.be.model.request.*;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.repository.TutorCertificateRepository;

import online.ondemandtutor.be.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TutorCertificateRepository tutorCertificateRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionService transactionService;

    // STUDENT GUI REQUEST KEM` CERTIFICATE

    public Account UpRole(UpRoleRequest upRoleRequest){
        Account account = authenticationService.getCurrentAccount();

        if(account != null){
            if (account.getRequestStatus() == StatusEnum.PENDING) {
                throw new BadRequestException("Your request is pending!");
            }

            TutorCertificate certificate = new TutorCertificate();
            certificate.setUrl(upRoleRequest.getCertificateUrl());
            certificate.setAccount(account);

            account.setRequestStatus(StatusEnum.PENDING);
            account.getTutorCertificates().add(certificate);
            SendUpRoleRegistrationToModerator(account);
            tutorCertificateRepository.save(certificate);
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
                    emailService.sendUpRoleRegistrationEmail(emailDetail);
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
                emailService.sendApprovedUpRoleRequestEmail(emailDetail);
            }
        };
        new Thread(r).start();
    }

    // lay danh sach tat ca nhung user dang yeu cau Up Role
    public List<Account> getAllAccountsHaveUpRoleRequest(){
        return authenticationRepository.findAccountByRequestStatus(StatusEnum.PENDING);
    }

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

    //monthly package
    private static final double UPGRADE_FEE = 100000;

    public Account RoleMonthlyPackage(MonthlyPackageRequest request){
        Account account = authenticationRepository.findAccountById(request.getAccountId());
        if(account != null){
            if(account.getMonthlyPackage() == MonthlyPackageEnum.ACTIVATED){
                throw new BadRequestException("You already bought a monthly package!");
            }
            Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
            if (wallet.getMoney() < UPGRADE_FEE) {
                throw new BadRequestException("Insufficient funds to upgrade.");
            }
            // Deduct the upgrade fee
            wallet.setMoney(wallet.getMoney() - UPGRADE_FEE);
            walletRepository.save(wallet);
            // Record the transaction
            transactionService.createTransaction(wallet, UPGRADE_FEE, TransactionEnum.MONTHLY_PACKAGE);
            account.setMonthlyPackage(MonthlyPackageEnum.ACTIVATED);
            account.setNextPaymentDate(LocalDateTime.now().plusMonths(1));
            sendMonthlyPackageConfirmationToTutor(account, "YOU JUST BOUGHT A MONTHLY PACKAGE!");
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }
    public void sendMonthlyPackageConfirmationToTutor(Account account, String msg){
        //copy tu ForgetPassword
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Response from PLATFORM for " + account.getEmail() + " MONTHLY PACKAGE!");
        emailDetail.setMsgBody(msg);
        // chờ FE gửi web chính thức
        emailDetail.setButtonValue("Let's create a new subject!");
        emailDetail.setFullName(account.getFullname());
        // chờ FE gửi link trang web
        emailDetail.setLink("http://ondemandtutor.online/login");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendApprovedUpRoleRequestEmail(emailDetail);
            }
        };
        new Thread(r).start();
    }


    //schedule countdown for role expired date
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void checkAndNotifyForMaintenancePayments() {
        List<Account> tutorAccounts = authenticationRepository.findAccountByRole(RoleEnum.TUTOR);
        for (Account account : tutorAccounts) {
            if (account.getNextPaymentDate() != null && account.getNextPaymentDate().isBefore(LocalDateTime.now().plusDays(7))) {
                sendMaintenanceNotification(account);
            }
            if (account.getNextPaymentDate() != null && account.getNextPaymentDate().isBefore(LocalDateTime.now())) {
                processMaintenancePayment(account);
            }
        }
    }

    private void sendMaintenanceNotification(Account account) {
        String subject = "Upcoming Maintenance Payment";
        String description = "You need to recharge 100000 VND to maintain your TUTOR role.";
        walletService.threadSendMail(account, subject, description);
    }

    private void processMaintenancePayment(Account account) {
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        if (wallet.getMoney() < 100000) {
            account.setRole(RoleEnum.STUDENT);
            account.setNextPaymentDate(null);
            authenticationRepository.save(account);
        } else {
            wallet.setMoney(wallet.getMoney() - 100000);
            walletRepository.save(wallet);
            transactionService.createTransaction(wallet, 100000, TransactionEnum.RECHARGE);
            account.setNextPaymentDate(LocalDateTime.now().plusMonths(1));
            authenticationRepository.save(account);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void checkAndNotifyTutorsForUnusedBalance() {
        List<Account> tutorAccounts = authenticationRepository.findAccountByRole(RoleEnum.TUTOR);
        for (Account tutor : tutorAccounts) {
            Wallet wallet = walletRepository.findWalletByAccountId(tutor.getId());
            boolean hasUnusedBalance = wallet.getMoney() >= UPGRADE_FEE;
            boolean isMonthlyPackageDeactivated = tutor.getMonthlyPackage() == MonthlyPackageEnum.DEACTIVATED;
            boolean hasNotPurchasedForOverAMonth = tutor.getNextPaymentDate() == null || tutor.getNextPaymentDate().isBefore(LocalDateTime.now().minusMonths(1));

            if (hasUnusedBalance && isMonthlyPackageDeactivated && hasNotPurchasedForOverAMonth) {
                sendUnusedBalanceNotification(tutor);
            }
        }
    }

    private void sendUnusedBalanceNotification(Account tutor) {
        String subject = "Unused Balance Alert";
        String description = "You have an unused balance in your account. Consider purchasing a monthly package to make the most out of our platform.";
        walletService.threadSendMail(tutor, subject, description);
    }
}
