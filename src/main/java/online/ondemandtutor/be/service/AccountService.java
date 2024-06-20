package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.GetAccountByIdRequest;
import online.ondemandtutor.be.model.UpRoleRequest;
import online.ondemandtutor.be.model.UpdateRequest;
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

    //chinh sua: 12:45 ngay 12/06/2024
    public Account getAccountById(long id){
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
