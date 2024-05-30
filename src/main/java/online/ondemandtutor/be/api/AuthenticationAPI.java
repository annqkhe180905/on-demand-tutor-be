package online.ondemandtutor.be.api;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.model.AccountResponse;
import online.ondemandtutor.be.model.LoginRequest;
import online.ondemandtutor.be.model.RegisterRequest;
import online.ondemandtutor.be.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("test") //tao ra funciton
    public ResponseEntity test(){
        return ResponseEntity.ok("test ok!");
    }

    @PostMapping("post")
    public ResponseEntity testPost(){
        return ResponseEntity.ok("post ok!");
    }

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
}
