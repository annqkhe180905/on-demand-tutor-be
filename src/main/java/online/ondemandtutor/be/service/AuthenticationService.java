package online.ondemandtutor.be.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.exception.AuthException;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.*;
import online.ondemandtutor.be.model.request.*;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.repository.WalletRepository;
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
    @Autowired
    private WalletRepository walletRepository;

    public Account register(RegisterRequest registerRequest){
        //test only
        Account existingAccount = authenticationRepository.findAccountByEmail(registerRequest.getEmail());
        if (existingAccount != null) {
            throw new BadRequestException("Email already in use");
        }

        Account account = new Account();
        account.setEmail(registerRequest.getEmail());
        account.setFullname(registerRequest.getFullname());
        account.setRole(RoleEnum.STUDENT); // mac dinh la role Student, neu muon thi up role len TUTOR sau
        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Wallet wallet = new Wallet();
        wallet.setMoney(0);
        account.setWallet(wallet);
        Account newAccount = authenticationRepository.save(account); //save to db
        wallet.setAccount(account);
        walletRepository.save(wallet);
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

        //test only
        Account account = authenticationRepository.findAccountByEmail(loginRequest.getEmail());
        if (account == null || !passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new AuthException("Email or password is not correct!");
        }
        // => account chuẩn

        if(account.isDeleted() == true){
            throw new BadRequestException("Please try another account!");
        }
        String token = tokenService.generateToken(account);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setEmail(account.getEmail());
        accountResponse.setToken(token);
        accountResponse.setFullname(account.getFullname());
        accountResponse.setRole(account.getRole());
        accountResponse.setId(account.getId());
        accountResponse.setPhone((account.getPhone()));
        accountResponse.setWallet((account.getWallet()));
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

                Wallet wallet = new Wallet();
                wallet.setMoney(0);
                account.setWallet(wallet);
                authenticationRepository.save(account); //save to db
                wallet.setAccount(account);
                walletRepository.save(wallet);

            }else{
                if(account.isDeleted() == true){
                    throw new BadRequestException("Please try another account!");
                }
            }

            if(accountResponse.getPhone() != null) accountResponse.setPhone((account.getPhone()));
            accountResponse.setId(account.getId());
            //fullname
            accountResponse.setFullname(account.getFullname());
            accountResponse.setRole(account.getRole());
            //email
            accountResponse.setEmail(account.getEmail());
            String token = tokenService.generateToken(account);
            accountResponse.setToken(token);
            accountResponse.setWallet((account.getWallet()));

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
        Account accountFilter = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return authenticationRepository.findAccountById(accountFilter.getId());
        return accountFilter;
    }

    public void ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        authenticationRepository.save(account);
    }
}
