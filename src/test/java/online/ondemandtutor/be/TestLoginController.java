//package online.ondemandtutor.be;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import online.ondemandtutor.be.api.AuthenticationAPI;
//import online.ondemandtutor.be.entity.Account;
//import online.ondemandtutor.be.enums.RoleEnum;
//import online.ondemandtutor.be.exception.AuthException;
//import online.ondemandtutor.be.exception.BadRequestException;
//import online.ondemandtutor.be.model.AccountResponse;
//import online.ondemandtutor.be.model.LoginRequest;
//import online.ondemandtutor.be.repository.AuthenticationRepository;
//import online.ondemandtutor.be.service.AuthenticationService;
//import online.ondemandtutor.be.service.TokenService;
//import online.ondemandtutor.be.validation.LoginValidation;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@RunWith(MockitoJUnitRunner.class)
////public class TestLoginController {
////    private MockMvc mockMvc;
////
////    ObjectMapper objectMapper = new ObjectMapper();
////    ObjectWriter objectWriter = objectMapper.writer();
////
////    @Mock
////    private AuthenticationManager authenticationManager;
////
////    @Mock
////    private AuthenticationRepository authenticationRepository;
////
////    @Mock
////    private TokenService tokenService;
////
////    @InjectMocks
////    private AuthenticationService authenticationService;
////
////    private LoginRequest validLoginRequest;
////    private Account validAccount;
////
////    @InjectMocks
////    private AuthenticationAPI authenticationAPI;
////
////    LoginRequest loginReq1 = new LoginRequest("admin", "admin");
////    LoginRequest loginReq2 = new LoginRequest("string!@gmail.com", "username");
////    LoginRequest loginReq3 = new LoginRequest(" annqk@gmail.com", "string");
////    LoginRequest loginReq4 = new LoginRequest("annqkhe180905@fpt.edu.vn", "String123!");
////    LoginRequest loginReq5 = new LoginRequest("", "String123!");
////
////    @BeforeEach
////    public void setUp() {
////        validLoginRequest = new LoginRequest("valid@email.com", "validPassword");
////        validAccount = new Account("valid@email.com", "validPassword", "John Doe", "USER", false, "1234567890", null);
////    }
////
//////    @Test
//////    public void testLogin_Success() throws Exception {
//////        Account actualResult = Account.builder()
//////                .email("")
//////                .password("string")
//////                .build();
//////
//////
//////    }
////
////    @Test
////    void testLoginSuccessful() {
////        when(authenticationRepository.findAccountByEmail(validLoginRequest.getEmail())).thenReturn(validAccount);
////        when(tokenService.generateToken(validAccount)).thenReturn("generatedToken");
////
////        AccountResponse response = authenticationService.login(validLoginRequest);
////
////        assertEquals(validAccount.getEmail(), response.getEmail());
////        assertEquals("generatedToken", response.getToken());
////        assertEquals(validAccount.getFullname(), response.getFullname());
////        assertEquals(validAccount.getRole(), response.getRole());
////        assertEquals(validAccount.getId(), response.getId());
////        assertEquals(validAccount.getPhone(), response.getPhone());
////        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
////    }
////
////    @Test
////    void testLoginFailedWithInvalidCredentials() {
////        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid credentials"));
////
////        assertThrows(AuthException.class, () -> authenticationService.login(validLoginRequest));
////    }
////
////    @Test
////    void testLoginFailedWithDeletedAccount() {
////        Account deletedAccount = new Account("valid@email.com", "validPassword", "John Doe", "USER", true, "1234567890", null);
////        when(authenticationRepository.findAccountByEmail(validLoginRequest.getEmail())).thenReturn(deletedAccount);
////
////        assertThrows(BadRequestException.class, () -> authenticationService.login(validLoginRequest));
////    }
////
////    //
////
//////    @Test
//////    void testLoginSuccessful() {
//////        when(authenticationRepository.findAccountByEmail(validLoginRequest.getEmail())).thenReturn(validAccount);
//////        when(tokenService.generateToken(validAccount)).thenReturn("generatedToken");
//////
//////        AccountResponse response = authenticationService.login(validLoginRequest);
//////
//////        assertEquals(validAccount.getEmail(), response.getEmail());
//////        assertEquals("generatedToken", response.getToken());
//////        assertEquals(validAccount.getFullname(), response.getFullname());
//////        assertEquals(validAccount.getRole(), response.getRole());
//////        assertEquals(validAccount.getId(), response.getId());
//////        assertEquals(validAccount.getPhone(), response.getPhone());
//////    }
//////
//////    @Test
//////    void testLoginFailedWithInvalidCredentials() {
//////        when(authenticationRepository.findAccountByEmail(validLoginRequest.getEmail())).thenThrow(new AuthException("Email or password is not correct!"));
//////
//////        assertThrows(AuthException.class, () -> authenticationService.login(validLoginRequest));
//////    }
//////
//////    @Test
//////    void testLoginFailedWithDeletedAccount() {
//////        Account deletedAccount = new Account("valid@email.com", "validPassword", "John Doe", "USER", true, "1234567890", null);
//////        when(authenticationRepository.findAccountByEmail(validLoginRequest.getEmail())).thenReturn(deletedAccount);
//////
//////        assertThrows(BadRequestException.class, () -> authenticationService.login(validLoginRequest));
//////    }
////
////}
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//@ExtendWith(MockitoExtension.class)
//public class TestLoginController {
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private TokenService tokenService;
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @Mock
//    private LoginValidation loginValidation;
//
//    @InjectMocks
//    private AuthenticationService authenticationService;
//
//    @InjectMocks
//    private AuthenticationAPI authenticationAPI;
//
//    private MockMvc mockMvc;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    ObjectWriter objectWriter = objectMapper.writer();
//
//    @BeforeEach
//    public void setUp() {
//        // Setup mock behavior or reset state before each test method
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationAPI).build();
//
//    }
//
//    // gia? su? moi exception deu dung (ko null, ko ki tu dac biet, ko khoang trang o nhung vi tri dau tien,
//    // email co 1 dau @, do dai ki tu khong vuot qua 256)
//
//    @Test
//    public void testLogin_Successful() {
//        // chuan bi thong tin de login
//        LoginRequest loginRequest = new LoginRequest("tet@yahoo.com", "password");
//        // doi tuong Account dc gia lap de? mock
//        Account mockAccount = new Account("tet@yahoo.com", "password", "Nguyen Quang Khanh An", "1234567890", RoleEnum.STUDENT, false);
//
//        //cau hinh hanh vi cua cac doi tuong mock
//        //khi goi phuong thuc findAccountByEmail cua authenticationRepository voi tham so truyen vao
//        // la loginRequest.getEmail()  ==>  tra ve mockAccount
//        when(authenticationRepository.findAccountByEmail(loginRequest.getEmail())).thenReturn(mockAccount);
//        //khi goi phuong thuc generateToken cua tokenService voi tham so truyen vao
//        // la mockAccount  ==>  tra ve mock_token
//        when(tokenService.generateToken(mockAccount)).thenReturn("mocked_token");
//
//        // response la ket qua tra ve cua phuong thuc LOGIN
//        AccountResponse response = authenticationService.login(loginRequest);
//
//        //kiem tra response co null k
//        //kiem tra cac thuoc tinh cua response phai tuong duong voi cac gia tri dc thiet lap trong mockAccount
//        //tham so truyen vao ben trai la EXPECTED RESULT, ben phai la ACTUAL RESULT
//        assertNotNull(response);
//        assertEquals(mockAccount.getEmail(), response.getEmail());
//        assertEquals("mocked_token", response.getToken());
//        assertEquals(mockAccount.getFullname(), response.getFullname());
//        assertEquals(mockAccount.getRole(), response.getRole());
//        assertEquals(mockAccount.getId(), response.getId());
//        assertEquals(mockAccount.getPhone(), response.getPhone());
//
//        //kiem tra phuong thuc authenticate cua authenticationManager co dc goi dung voi tham so tuong ung k
//        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequest.getEmail(),
//                loginRequest.getPassword()
//        ));
//        //kiem tra phuong thuc findAccountByEmail cua authenticationRepository co dc goi dung voi email tuong ung k
//        verify(authenticationRepository).findAccountByEmail(loginRequest.getEmail());
//        //kiem tra phuong thuc generateToken cua tokenService co dc goi dung voi mockAccount tuong ung k
//        verify(tokenService).generateToken(mockAccount);
//    }
//    // ket qua output se la mac dinh, bat chap input dung hay sai
//
//    @Test
//    public void testLogin_AccountDeleted() {
//        // chuan bi thong tin de login
//        LoginRequest loginRequest = new LoginRequest("deleted@yahoo.com", "password");
//        // thiet lap rang account da bi deleted
//        Account deletedAccount = new Account();
//        deletedAccount.setDeleted(true);
//
//        when(authenticationRepository.findAccountByEmail(loginRequest.getEmail())).thenReturn(deletedAccount);
//
//        // vi tai khoan da bi xoa (isDeleted = true)
//        // method nay nem ra 1 ngoai le exception
//        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
//            authenticationService.login(loginRequest);
//        });
//        assertEquals("Please try another account!", exception.getMessage());
//
//        //kiem tra phuong thuc findAccountByEmail cua authenticationRepository co dc goi dung voi email tuong ung k
//        verify(authenticationRepository).findAccountByEmail(loginRequest.getEmail());
//        //dam bao khong co tuong tac nao voi token
//        verifyNoInteractions(tokenService);
//    }
//
//    @Test
//    public void testLogin_InvalidCredentials() {
//        // chuan bi thong tin de login
//        LoginRequest loginRequest = new LoginRequest("notexisted@yahoo.com", "password");
//        // thiet lap rang account khong co trong he thong db
//        when(authenticationRepository.findAccountByEmail(loginRequest.getEmail())).thenReturn(null);
//
//        // vi tai khoan khong ton tai truoc day trong db
//        // method nay nem ra 1 ngoai le exception
//        AuthException exception = assertThrows(AuthException.class, () -> {
//            authenticationService.login(loginRequest);
//        });
//        assertEquals("Email or password is not correct!", exception.getMessage());
//
//        //kiem tra phuong thuc findAccountByEmail cua authenticationRepository co dc goi dung voi email tuong ung k
//        verify(authenticationRepository).findAccountByEmail(loginRequest.getEmail());
//        //dam bao khong co tuong tac nao voi token
//        verifyNoInteractions(tokenService);
//    }
//
//    @Test
//    public void testLogin_InvalidEmail() {
//        String invalidEmail = " invalid_email";
//        assertFalse(loginValidation.isValidUsername(invalidEmail));
//
//        LoginRequest loginRequest = new LoginRequest(invalidEmail, "password");
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            authenticationService.login(loginRequest);
//        });
//
////        verifyNoInteractions(authenticationManager);
////        verifyNoInteractions(authenticationRepository);
////        verifyNoInteractions(tokenService);
//    }
//
//    @Test
//    public void testLogin_ValidEmail() {
//        String validEmail = "annqk@fpt.edu.vn";
//        //assertFalse kiem tra dieu kien co thoa man hay khong
//        //boi vi Validation kiem tra xem email co thoa man hay khong
//        // neu Validation dua ra cac truong hop sai
//        // o day ta phai dung assertTrue de check xem Validation co tra ve false hay khong
//        assertTrue(loginValidation.isValidUsername(validEmail));
//
//        LoginRequest loginRequest = new LoginRequest(validEmail, "password");
//        Account mockAccount = Account.builder()
//                .email("annqk@fpt.edu.vn")
//                .password("password")
//                .fullname("Nguyen Quang Khanh An")
//                .role(RoleEnum.STUDENT)
//                .phone("1234567890")
//                .isDeleted(false)
//                .build();
//
////        Account mockAccount = new Account("tet@yahoo.com", "password", "Nguyen Quang Khanh An", "1234567890", RoleEnum.STUDENT, false);
////        System.out.println(mockAccount.getEmail());
//
//        when(authenticationRepository.findAccountByEmail(loginRequest.getEmail())).thenReturn(mockAccount);
//        when(tokenService.generateToken(mockAccount)).thenReturn("mocked_token");
//
//        AccountResponse response = authenticationService.login(loginRequest);
//
//        assertNotNull(response);
//        assertEquals(mockAccount.getEmail(), response.getEmail());
//        assertEquals("mocked_token", response.getToken());
//        assertEquals(mockAccount.getFullname(), response.getFullname());
//        assertEquals(mockAccount.getRole(), response.getRole());
//        assertEquals(mockAccount.getId(), response.getId());
//        assertEquals(mockAccount.getPhone(), response.getPhone());
//
//        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequest.getEmail(),
//                loginRequest.getPassword()
//        ));
//        verify(authenticationRepository).findAccountByEmail(loginRequest.getEmail());
//        verify(tokenService).generateToken(mockAccount);
//    }
//
//    @Test
//    public void testLogin_InvalidPassword() {
//        String validEmail = "annqkhe180905@fpt.edu.vn";
//        String invalidPassword = "short";
//        assertFalse(loginValidation.isValidPassword(invalidPassword));
//
//        LoginRequest loginRequest = new LoginRequest(validEmail, invalidPassword);
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            authenticationService.login(loginRequest);
//        });
//
////        verifyNoInteractions(authenticationManager);
////        verifyNoInteractions(authenticationRepository);
////        verifyNoInteractions(tokenService);
//    }
//
//    @Test
//    public void testLogin_ValidPassword() {
//        String validPassword = "String123!";
//
//        assertTrue(loginValidation.isValidPassword(validPassword));
//
//        LoginRequest loginRequest = new LoginRequest("annqkhe180905@fpt.edu.vn", validPassword);
//        Account mockAccount = Account.builder()
//                .email("annqkhe180905@fpt.edu.vn")
//                .password("String123!")
//                .fullname("Nguyen Quang Khanh An")
//                .role(RoleEnum.STUDENT)
//                .phone("1234567890")
//                .isDeleted(false)
//                .build();
//
//        when(authenticationRepository.findAccountByEmail(loginRequest.getEmail())).thenReturn(mockAccount);
//        when(tokenService.generateToken(mockAccount)).thenReturn("mocked_token");
//
//        AccountResponse response = authenticationService.login(loginRequest);
//
//        assertNotNull(response);
//        assertEquals(mockAccount.getEmail(), response.getEmail());
//        assertEquals("mocked_token", response.getToken());
//        assertEquals(mockAccount.getFullname(), response.getFullname());
//        assertEquals(mockAccount.getRole(), response.getRole());
//        assertEquals(mockAccount.getId(), response.getId());
//        assertEquals(mockAccount.getPhone(), response.getPhone());
//
//        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequest.getEmail(),
//                loginRequest.getPassword()
//        ));
//        verify(authenticationRepository).findAccountByEmail(loginRequest.getEmail());
//        verify(tokenService).generateToken(mockAccount);
//    }
//}
