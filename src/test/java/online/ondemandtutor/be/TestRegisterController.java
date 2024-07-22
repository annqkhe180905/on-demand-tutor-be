//package online.ondemandtutor.be;//import online.ondemandtutor.be.repository.AuthenticationRepository;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////import org.springframework.security.crypto.password.PasswordEncoder;
////
////@ExtendWith(MockitoExtension.class)
////public class TestRegisterController {
////
////    @Mock
////    private AuthenticationRepository authenticationRepository;
////
////    @Mock
////    private PasswordEncoder passwordEncoder;
////
////    @Mock
////    private RegisterValidation registerValidation;
////
////    @InjectMocks
////    private AuthenticationService authenticationService;
////
////    @InjectMocks
////    private AuthenticationAPI authenticationAPI;
////
////    private MockMvc mockMvc;
////
////    ObjectMapper objectMapper = new ObjectMapper();
////    ObjectWriter objectWriter = objectMapper.writer();
////
////    @BeforeEach
////    public void setUp() {
////        MockitoAnnotations.initMocks(this);
////        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationAPI).build();
////    }
////
////    @Test
////    public void testRegister_Successful() {
////        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
////        registerRequest.setConfirmPassword("ValidPassword123!");
////
////        Account mockAccount = new Account("john@example.com", "hashedPassword", "John Doe", "123456789", RoleEnum.USER, false);
////
////        when(authenticationRepository.findAccountByEmail(registerRequest.getEmail())).thenReturn(null);
////        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedPassword");
////        when(authenticationRepository.save(any(Account.class))).thenReturn(mockAccount);
////
////        AccountResponse response = authenticationService.register(registerRequest);
////
////        assertNotNull(response);
////        assertEquals(mockAccount.getEmail(), response.getEmail());
////        assertEquals(mockAccount.getFullname(), response.getFullname());
////        assertEquals(mockAccount.getRole(), response.getRole());
////        assertEquals(mockAccount.getPhone(), response.getPhone());
////
////        verify(authenticationRepository).findAccountByEmail(registerRequest.getEmail());
////        verify(passwordEncoder).encode(registerRequest.getPassword());
////        verify(authenticationRepository).save(any(Account.class));
////    }
////
////    @Test
////    public void testRegister_EmailAlreadyExists() {
////        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
////        registerRequest.setConfirmPassword("ValidPassword123!");
////
////        Account existingAccount = new Account();
////        existingAccount.setEmail(registerRequest.getEmail());
////
////        when(authenticationRepository.findAccountByEmail(registerRequest.getEmail())).thenReturn(existingAccount);
////
////        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
////            authenticationService.register(registerRequest);
////        });
////
////        assertEquals("Email is already in use", exception.getMessage());
////
////        verify(authenticationRepository).findAccountByEmail(registerRequest.getEmail());
////        verifyNoMoreInteractions(passwordEncoder);
////        verifyNoMoreInteractions(authenticationRepository);
////    }
////
////    @Test
////    public void testRegister_InvalidEmail() {
////        String invalidEmail = "invalid_email";
////        assertFalse(registerValidation.isValidEmail(invalidEmail));
////
////        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", invalidEmail);
////        registerRequest.setConfirmPassword("ValidPassword123!");
////
////        Exception exception = assertThrows(InvalidFieldException.class, () -> {
////            authenticationService.register(registerRequest);
////        });
////
////        assertEquals("Email is not valid", exception.getMessage());
////
////        verifyNoInteractions(passwordEncoder);
////        verifyNoInteractions(authenticationRepository);
////    }
////
////    @Test
////    public void testRegister_InvalidPassword() {
////        String validEmail = "john@example.com";
////        String invalidPassword = "short";
////        assertFalse(registerValidation.isValidPassword(invalidPassword));
////
////        RegisterRequest registerRequest = new RegisterRequest("John Doe", invalidPassword, "123456789", validEmail);
////        registerRequest.setConfirmPassword(invalidPassword);
////
////        Exception exception = assertThrows(InvalidFieldException.class, () -> {
////            authenticationService.register(registerRequest);
////        });
////
////        assertEquals("Password must be at least 8 characters", exception.getMessage());
////
////        verifyNoInteractions(passwordEncoder);
////        verifyNoInteractions(authenticationRepository);
////    }
////
////    @Test
////    public void testRegister_PasswordMismatch() {
////        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
////        registerRequest.setConfirmPassword("DifferentPassword123!");
////
////        Exception exception = assertThrows(InvalidFieldException.class, () -> {
////            authenticationService.register(registerRequest);
////        });
////
////        assertEquals("Password and confirm password must match", exception.getMessage());
////
////        verifyNoInteractions(passwordEncoder);
////        verifyNoInteractions(authenticationRepository);
////    }
////}
//
//import online.ondemandtutor.be.enums.RoleEnum;
//import online.ondemandtutor.be.repository.AuthenticationRepository;
//import online.ondemandtutor.be.service.AuthenticationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import online.ondemandtutor.be.entity.Account;
//import online.ondemandtutor.be.model.RegisterRequest;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@ExtendWith(MockitoExtension.class)
//public class TestRegisterController{
//
//    @InjectMocks
//    private AuthenticationService authenticationService;
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @Mock
//    PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("register should save and return account when valid request is provided")
//    public void registerHappyCaseTesting() {
//        // chuan bi thong tin input
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("annqkhe180905@fpt.edu.vn");
//        registerRequest.setFullname("Khanh An");
//        registerRequest.setPhone("1234567890");
//        registerRequest.setPassword("Password123!");
//
//        Account account = new Account();
//        account.setEmail(registerRequest.getEmail());
//        account.setFullname(registerRequest.getFullname());
//        account.setPhone(registerRequest.getPhone());
//        account.setPassword("encodedPassword"); // dung password da duoc encode
//        account.setRole(RoleEnum.STUDENT);
//
//        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
//        when(authenticationRepository.save(any(Account.class))).thenReturn(account);
//
//        // dang ky tai khoan
//        Account result = authenticationService.register(registerRequest);
//
//        // so sanh
//        assertNotNull(result);
//        assertEquals(registerRequest.getEmail(), result.getEmail());
//        assertEquals(registerRequest.getFullname(), result.getFullname());
//        assertEquals(registerRequest.getPhone(), result.getPhone());
//        assertEquals("encodedPassword", result.getPassword()); // Direct comparison with encoded password
//        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
//        verify(authenticationRepository, times(1)).save(any(Account.class));
//    }
//
//    @Test
//    // hien thi dong thong bao ben trai
//    @DisplayName("register should throw exception when duplicate email is provided")
//    public void registerButDuplicatedEmail() {
//        // chuan bi thong tin input
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("annqkhe180905@fpt.edu.vn");
//        registerRequest.setFullname("Khanh An");
//        registerRequest.setPhone("1234567890");
//        registerRequest.setPassword("Password123!");
//
//        // chuan bi thong tin gia? co san trong db
//        Account existingAccount = new Account();
//        existingAccount.setEmail(registerRequest.getEmail());
//
//        when(authenticationRepository.findAccountByEmail(registerRequest.getEmail())).thenReturn(existingAccount);
//
//        // kiem tra co bi trung\ email hay khong
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            authenticationService.register(registerRequest);
//        });
//
//        //o vai chuong dung nay :))))
//        assertEquals("Email already in use", exception.getMessage());
//
//        verify(authenticationRepository, times(1)).findAccountByEmail(registerRequest.getEmail());
//    }
//}
