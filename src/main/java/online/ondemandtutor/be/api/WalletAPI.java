package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.model.CreateUrlResponse;
import online.ondemandtutor.be.model.request.RechargeRequest;
import online.ondemandtutor.be.model.UpdateMoneyResponse;
import online.ondemandtutor.be.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('TUTOR')")
public class WalletAPI {

    @Autowired
    WalletService walletService;

    @PostMapping("/request-recharge-vnpay")
    public ResponseEntity createUrl(@RequestBody RechargeRequest rechargeRequestDTO) throws Exception {
        try{
            String url = walletService.createUrl(rechargeRequestDTO);
            return  ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/walletDetail/{id}")
    public ResponseEntity walletDetail(@PathVariable Long id) throws Exception {
        Wallet wallet = walletService.walletDetail(id);
        return  ResponseEntity.ok(wallet);

    }

    @PutMapping("/update-money")
    public ResponseEntity updateMoney(@RequestBody UpdateMoneyResponse response) {
        Wallet wallet1 = walletService.updateMoney(response);
        return ResponseEntity.ok(wallet1);
    }
}
