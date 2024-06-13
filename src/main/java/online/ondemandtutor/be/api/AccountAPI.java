package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.model.GetAccountByIdRequest;
import online.ondemandtutor.be.model.UpRoleRequest;
import online.ondemandtutor.be.model.UpdateRequest;
import online.ondemandtutor.be.service.AccountService;
import online.ondemandtutor.be.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class AccountAPI {

    @Autowired
    AccountService accountService;

    @PostMapping("/up-role")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void upRole(@RequestBody UpRoleRequest upRoleRequest){
        accountService.UpRole(upRoleRequest);
    }
    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> showAll() {
        List<Account> printAll = accountService.getAllAccounts();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/account/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity showAccountById(@PathVariable long id) {
        Account printOne = accountService.getAccountById(id);
        return ResponseEntity.ok(printOne);
    }

    @PutMapping("account")
    public ResponseEntity updateThemself(@RequestBody UpdateRequest updateRequest) {
        Account account = accountService.updateAccount(updateRequest);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("account/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity changeStatus(@PathVariable long id) {
        Account account = accountService.changeStatusByAdmin(id);
        return ResponseEntity.ok(account);
    }
}

