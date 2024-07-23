package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Transaction;
import online.ondemandtutor.be.entity.Wallet;
import online.ondemandtutor.be.enums.TransactionEnum;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.CreateUrlResponse;
import online.ondemandtutor.be.model.CreateWalletUrlResponse;
import online.ondemandtutor.be.model.request.RechargeRequest;
import online.ondemandtutor.be.model.UpdateMoneyResponse;
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
        String orderId = UUID.randomUUID().toString().substring(0,9);

        Account account = authenticationService.getCurrentAccount();

        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());

        Transaction transaction = new Transaction();

        transaction.setAmount(Double.parseDouble(rechargeRequestDTO.getAmount()));
//        transaction.setTransactionType(TransactionEnum.PENDING);
        transaction.setTo(wallet);
        transaction.setTransactionDate(formattedCreateDate);
        transaction.setDescription("Recharge");
        Transaction transactionReturn = transactionRepository.save(transaction);

        String tmnCode = "I7C7L45T";
        String secretKey = "C4SEJ9L30U3YRBJJSCRNIQYN1WB1A2WM";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

//        String returnUrl = "http://ondemandtutor.online/user-profile/wallet?id="+transactionReturn.getId();
        String returnUrl = "http://ondemandtutor.online/payments?accountId=" + account.getId();

        String currCode = "VND";
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", orderId);
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma giao dich: " + orderId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", rechargeRequestDTO.getAmount() + "00");
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

//    public CreateWalletUrlResponse createUrl(RechargeRequest rechargeRequestDTO) throws NoSuchAlgorithmException, InvalidKeyException, Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        LocalDateTime createDate = LocalDateTime.now();
//        String formattedCreateDate = createDate.format(formatter);
//        String orderId = UUID.randomUUID().toString().substring(0, 9);
//
//        Account account = authenticationService.getCurrentAccount();
//        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
//
//        Transaction transaction = new Transaction();
//        transaction.setAmount(Double.parseDouble(rechargeRequestDTO.getAmount()));
//        transaction.setTo(wallet);
//        transaction.setTransactionDate(formattedCreateDate);
//        transaction.setDescription("Recharge");
//        Transaction transactionReturn = transactionRepository.save(transaction);
//
//        String tmnCode = "I7C7L45T";
//        String secretKey = "C4SEJ9L30U3YRBJJSCRNIQYN1WB1A2WM";
//        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
//        String returnUrl = "http://ondemandtutor.online/payments";
//        String currCode = "VND";
//
//        Map<String, String> vnpParams = new TreeMap<>();
//        vnpParams.put("vnp_Version", "2.1.0");
//        vnpParams.put("vnp_Command", "pay");
//        vnpParams.put("vnp_TmnCode", tmnCode);
//        vnpParams.put("vnp_Locale", "vn");
//        vnpParams.put("vnp_CurrCode", currCode);
//        vnpParams.put("vnp_TxnRef", orderId);
//        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma giao dich: " + orderId);
//        vnpParams.put("vnp_OrderType", "other");
//        vnpParams.put("vnp_Amount", rechargeRequestDTO.getAmount() + "00");
//        vnpParams.put("vnp_ReturnUrl", returnUrl);
//        vnpParams.put("vnp_CreateDate", formattedCreateDate);
//        vnpParams.put("vnp_IpAddr", "167.71.202.18");
//
//        StringBuilder signDataBuilder = new StringBuilder();
//        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
//            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
//            signDataBuilder.append("=");
//            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
//            signDataBuilder.append("&");
//        }
//        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'
//
//        String signData = signDataBuilder.toString();
//        String signed = generateHMAC(secretKey, signData);
//
//        vnpParams.put("vnp_SecureHash", signed);
//
//        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
//        urlBuilder.append("?");
//        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
//            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
//            urlBuilder.append("=");
//            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
//            urlBuilder.append("&");
//        }
//        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'
//
//        String url = urlBuilder.toString();
//        return new CreateWalletUrlResponse(url, account.getId());
//    }


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

    public Wallet updateMoney(UpdateMoneyResponse response){
        Account account = authenticationService.getCurrentAccount();
        Wallet wallet = walletRepository.findWalletByAccountId(account.getId());
        if(response.getTutorId() == account.getId()){
            wallet.setMoney(wallet.getMoney() + response.getMoney());
            walletRepository.save(wallet);
            return wallet;
        }
        else{
            throw new BadRequestException("You are not allowed to do this action!");
        }
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
