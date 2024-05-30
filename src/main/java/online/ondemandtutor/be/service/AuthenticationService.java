package online.ondemandtutor.be.service;

import online.ondemandtutor.be.model.AccountResponse;
import online.ondemandtutor.be.model.LoginRequest;
import online.ondemandtutor.be.model.RegisterRequest;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import online.ondemandtutor.be.entity.Account;

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

    public Account register(RegisterRequest registerRequest){
        Account account = new Account();
        account.setPhone(registerRequest.getPhone());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Account newAccount = authenticationRepository.save(account); //save to db

        return newAccount;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getPhone(),
                loginRequest.getPassword()
        ));
        // => account chuẩn

        Account account = authenticationRepository.findAccountByPhone(loginRequest.getPhone());
        String token = tokenService.generateToken(account);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setPhone(account.getPhone());
        accountResponse.setToken(token);
//        accountResponse.setEmail(account.getEmail());
//        accountResponse.setFullName(account.getFullName());
//        accountResponse.setRole(account.getRole());

        return accountResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return authenticationRepository.findAccountByPhone(phone);
    }
}
