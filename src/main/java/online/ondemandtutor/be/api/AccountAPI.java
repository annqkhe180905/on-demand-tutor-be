package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.model.UpRoleRequest;
import online.ondemandtutor.be.model.UpRoleRequestByAccountId;
import online.ondemandtutor.be.model.UpdateRequest;
import online.ondemandtutor.be.service.AccountService;
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
    public ResponseEntity upRole(@RequestBody UpRoleRequest request){
        return ResponseEntity.ok(accountService.UpRole(request));
    }

    @PostMapping("/approved-up-role")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public void approvedUpRole(@RequestBody UpRoleRequestByAccountId id){
        accountService.ApprovedUpRole(id);
    }

    @PostMapping("/rejected-up-role")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public void rejectedUpRole(@RequestBody UpRoleRequestByAccountId id){
        accountService.RejectedUpRole(id);
    }

    @GetMapping("/pending-accounts")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<List<Account>> getPendingAccount (){
        List<Account> printAll = accountService.getAllAccountsHaveUpRoleRequest();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> showAll() {
        List<Account> printAll = accountService.getAllAccounts();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/account/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity showAccountById(@PathVariable Long id) {
        Account printOne = accountService.getAccountById(id);
        return ResponseEntity.ok(printOne);
    }

    //lay danh sach user is deleted
    @GetMapping("/accounts-is-deleted")
    public ResponseEntity showAccountsIsDeleted() {
        List<Account> printAll = accountService.getAllAccountsIsDeleted();
        return ResponseEntity.ok(printAll);
    }

    @GetMapping("/accounts-isnt-deleted")
    public ResponseEntity showAccountsIsNotDeleted() {
        List<Account> printAll = accountService.getAllAccountsIsNotDeleted();
        return ResponseEntity.ok(printAll);
    }

    @PutMapping("account")
    public ResponseEntity updateThemself(@RequestBody UpdateRequest updateRequest) {
        Account account = accountService.updateAccount(updateRequest);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("account/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity changeStatusIsDeleted(@PathVariable Long id) {
        Account account = accountService.changeStatusIsDeletedByAdmin(id);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("account/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity changeStatusIsNotDeleted(@PathVariable Long id) {
        Account account = accountService.changeStatusIsNotDeletedByAdmin(id);
        return ResponseEntity.ok(account);
    }

}

