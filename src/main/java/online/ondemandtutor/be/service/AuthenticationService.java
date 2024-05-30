package online.ondemandtutor.be.service;

import online.ondemandtutor.be.model.RegisterRequest;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import online.ondemandtutor.be.entity.Account;

@Service

public class AuthenticationService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    public Account register(RegisterRequest registerRequest){
        Account account = new Account();
        account.setPhone(registerRequest.getPhone());
        account.setPassword(registerRequest.getPassword());

        Account newAccount = authenticationRepository.save(account); //save to db

        return newAccount;
    }
}
