package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Category;
import online.ondemandtutor.be.model.*;
import online.ondemandtutor.be.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        Account account = authenticationService.register(registerRequest);
        return ResponseEntity.ok(account);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        AccountResponse accResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(accResponse);
    }

    @PostMapping("/login-google")
    public ResponseEntity<AccountResponse> loginGg(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        return ResponseEntity.ok(authenticationService.loginGoogle(loginGoogleRequest));
    }

    @PostMapping("/forget-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        authenticationService.ForgotPassword(forgotPasswordRequest);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        authenticationService.ResetPassword(resetPasswordRequest);
    }

    @PostMapping("/up-role")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void upRole(@RequestBody UpRoleRequest upRoleRequest){
        authenticationService.UpRole(upRoleRequest);
    }
    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Account>> showAll() {
        List<Account> printAll = authenticationService.getAllAccounts();
        return ResponseEntity.ok(printAll);
    }

    @PutMapping("account/{id}")
    public ResponseEntity updateThemself(@PathVariable long id, @RequestBody AccountRequest accountRequest) {
        Account account = authenticationService.updateAccount(accountRequest, id);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("account/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity changeStatus(@PathVariable long id) {
        Account account = authenticationService.changeStatusByAdmin(id);
        return ResponseEntity.ok(account);
    }


}
