package online.ondemandtutor.be.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import online.ondemandtutor.be.entity.Category;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.exception.AuthException;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.*;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import online.ondemandtutor.be.entity.Account;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    @Autowired
    EmailService emailService;

    public Account register(RegisterRequest registerRequest){
        Account account = new Account();
        account.setEmail(registerRequest.getEmail());
        account.setFullname(registerRequest.getFullname());
        account.setRole(RoleEnum.STUDENT); // mac dinh la role Student, neu muon thi up role len TUTOR sau
        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Account newAccount = authenticationRepository.save(account); //save to db

        return newAccount;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));
        }
        catch(Exception e){
            throw new AuthException("Email or password is not correct!");

        }

        // => account chuẩn

        Account account = authenticationRepository.findAccountByEmail(loginRequest.getEmail());
        String token = tokenService.generateToken(account);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setEmail(account.getEmail());
        accountResponse.setToken(token);
        accountResponse.setEmail(account.getEmail());
        accountResponse.setFullname(account.getFullname());
        accountResponse.setRole(account.getRole());

        return accountResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authenticationRepository.findAccountByEmail(email);
    }


    public AccountResponse loginGoogle(LoginGoogleRequest loginGoogleRequest) {
        AccountResponse accountResponse = new AccountResponse();
        try{
            Account account;
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = firebaseToken.getEmail();
            account  = authenticationRepository.findAccountByEmail(email);

            if(account == null){
                 account = new Account();
                //fullName
                account.setFullname(firebaseToken.getName());
                //email
                account.setEmail(firebaseToken.getEmail());
                account.setRole(RoleEnum.STUDENT);
                account = authenticationRepository.save(account);
            }

            accountResponse.setId(account.getId());
            //fullname
            accountResponse.setFullname(account.getFullname());
            accountResponse.setRole(account.getRole());
            //email
            accountResponse.setEmail(account.getEmail());
            String token = tokenService.generateToken(account);
            accountResponse.setToken(token);

        }catch(Exception e){
                System.out.println(e);
        }
        return accountResponse;
    }


    public void ForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Account account = authenticationRepository.findAccountByEmail(forgotPasswordRequest.getEmail());
        if(account == null){
            try {
                throw new BadRequestException("Account is not found!");
            }catch (RuntimeException e){
                throw new RuntimeException(e);
            }
        }
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Reset password for account " + account.getEmail() + "!");
        emailDetail.setMsgBody("");
        emailDetail.setButtonValue("Reset Password");
        emailDetail.setFullName(account.getFullname());
        // chờ FE gửi link web reset password
        emailDetail.setLink("http://ondemandtutor.online/reset-password?token=" + tokenService.generateToken(account));
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMailTemplate(emailDetail);
            }
        };
        new Thread(r).start();
    }


    public Account getCurrentAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        authenticationRepository.save(account);
    }

    //them vo github branch UpRole
    public Account UpRole(UpRoleRequest upRoleRequest){
        Account account = authenticationRepository.findAccountByEmail(upRoleRequest.getEmail());
        if(account != null){
            account.setRole(RoleEnum.TUTOR);
            return authenticationRepository.save(account);
        }
        else{
            throw new BadRequestException("Account is not found!");
        }
    }

    public List<Account> getAllAccounts(){
        return authenticationRepository.findAccountByIsDeletedFalse();
    }


    public Account updateAccount(AccountRequest accountRequest, long id) {
        Account account = authenticationRepository.findAccountByIdAndIsDeletedFalse(id);

        account.setPhone(accountRequest.getPhone());
        account.setFullname(accountRequest.getFullname());

        Account newAccount = authenticationRepository.save(account);
        return newAccount;
    }

    public Account changeStatusByAdmin (long id){
        Account account = authenticationRepository.findAccountByIdAndIsDeletedFalse(id);
        if(account != null){
            account.setDeleted(true);
            return authenticationRepository.save(account);
        }
        else {
            throw new BadRequestException("Account is not found!");
        }
    }
}
