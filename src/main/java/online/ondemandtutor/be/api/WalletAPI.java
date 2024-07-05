package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.model.RechargeRequest;
import online.ondemandtutor.be.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class WalletAPI {

    @Autowired
    WalletService walletService;

    @PostMapping("/request-recharge-vnpay")
    public ResponseEntity createUrl(@RequestBody RechargeRequest rechargeRequestDTO) throws Exception {
        String url = walletService.createUrl(rechargeRequestDTO);
        return  ResponseEntity.ok(url);

    }

    @PutMapping("/recharge/{id}")
    public ResponseEntity recharge(@PathVariable Long id) throws Exception {
        Wallet wallet = walletService.recharge(id);
        return  ResponseEntity.ok(wallet);
    }

    @GetMapping("/walletDetail/{id}")
    public ResponseEntity walletDetail(@PathVariable Long id) throws Exception {
        Wallet wallet = walletService.walletDetail(id);
        return  ResponseEntity.ok(wallet);

    }

}
