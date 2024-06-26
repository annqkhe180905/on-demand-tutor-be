package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Transaction;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.enums.TransactionEnum;
import online.ondemandtutor.be.model.RechargeRequest;
import online.ondemandtutor.be.repository.TransactionRepository;
import online.ondemandtutor.be.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    TransactionRepository transactionRepository;

    public String createUrl(RechargeRequest rechargeRequestDTO) throws NoSuchAlgorithmException, InvalidKeyException, Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        Account account = authenticationService.getCurrentAccount();

        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());

        Transaction transaction = new Transaction();

        transaction.setAmount(Double.parseDouble(rechargeRequestDTO.getAmount()));
        transaction.setTransactionType(TransactionEnum.PENDING);
        transaction.setTo(wallet);
        transaction.setTransactionDate(formattedCreateDate);
        transaction.setDescription("Recharge");
        Transaction transactionReturn = transactionRepository.save(transaction);

        String tmnCode = "I7C7L45T";
        String secretKey = "C4SEJ9L30U3YRBJJSCRNIQYN1WB1A2WM";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://ondemandtutor.online/user-profile/wallet?id="+transactionReturn.getId();

        String currCode = "VND";
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", rechargeRequestDTO.getAmount() +"00");
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "167.71.202.18");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }


    public Wallet recharge(Long id) {
        Account account = authenticationService.getCurrentAccount();
        Transaction transaction = transactionRepository.findTransactionById(id);
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        if(transaction.getTransactionType().equals(TransactionEnum.PENDING)) {
            if(wallet.getId() == transaction.getTo().getId()){
                wallet.setBalance(wallet.getBalance() + transaction.getAmount());
            }
        }
        else{
            throw new RuntimeException("Reload");
        }
        transaction.setTransactionType(TransactionEnum.RECHARGE);

        transactionRepository.save(transaction);
        return walletRepository.save(wallet);
    }

    public  Wallet walletDetail(Long id) {
        return  walletRepository.findWalletByAccountId(id);
    }

    public void threadSendMail(Account account,String subject, String description){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendTransactionMail(account,subject,description);
            }

        };
        new Thread(r).start();
    }
}
