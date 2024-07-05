//package online.ondemandtutor.be;//package online.ondemandtutor.be;
////
////import online.ondemandtutor.be.entity.Account;
////import online.ondemandtutor.be.exception.BadRequestException;
////import online.ondemandtutor.be.model.UpdateRequest;
////import online.ondemandtutor.be.repository.AuthenticationRepository;
////import online.ondemandtutor.be.service.AccountService;
////import online.ondemandtutor.be.service.AuthenticationService;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.MockitoAnnotations;
////import org.mockito.junit.jupiter.MockitoExtension;
////
////import static org.hamcrest.Matchers.any;
////import static org.junit.jupiter.api.Assertions.assertEquals;
////import static org.junit.jupiter.api.Assertions.assertThrows;
////import static org.mockito.Mockito.when;
////
////@ExtendWith(MockitoExtension.class)
////public class UpdateProfileTest {
////    @InjectMocks
////    private AccountService accountService;
////
////    @Mock
////    private AuthenticationRepository authenticationRepository;
////
////    @Mock
////    private AuthenticationService authenticationService;
////
////    @BeforeEach
////    public void setup() {
////        MockitoAnnotations.openMocks(this);
////    }
////
////    @Test
////    public void updateAccount_HappyCase() {
////        // Arrange
////        UpdateRequest updateRequest = new UpdateRequest();
////        updateRequest.setPhone("1234567890");
////        updateRequest.setFullname("Khanh An");
////
////        Account existingAccount = new Account();
////        existingAccount.setPhone("0987654321");
////        existingAccount.setFullname("An Khanh");
////
////        // Mock the behavior of getCurrentAccount to return existingAccount
////        when(authenticationService.getCurrentAccount()).thenReturn(existingAccount);
////
////        // Mock the save method to return the account being saved
////        when(authenticationRepository.save(any(Account.class))).thenAnswer(invocation -> {
////            Account account = invocation.getArgument(0);
////            System.out.println("Mock save method called with: " + account);
////            return account;
////        });
////
////        // Act
////        Account result = accountService.updateAccount(updateRequest);
////
////        // Assert
////        assertEquals(updateRequest.getPhone(), result.getPhone());
////        assertEquals(updateRequest.getFullname(), result.getFullname());
////    }
////
////    @Test
////    public void updateAccount_UnhappyCase() {
////        // Arrange
////        UpdateRequest updateRequest = new UpdateRequest();
////        updateRequest.setPhone("1234567890");
////        updateRequest.setFullname("Khanh An");
////
////        when(authenticationService.getCurrentAccount()).thenReturn(null);
////
////        // Act and Assert
////        assertThrows(BadRequestException.class, () -> {
////            accountService.updateAccount(updateRequest);
////        });
////    }
////
////
////}
//
////package online.ondemandtutor.be;
////
////
////import online.ondemandtutor.be.api.AuthenticationAPI;
////import online.ondemandtutor.be.entity.Account;
////import online.ondemandtutor.be.enums.RoleEnum;
////import online.ondemandtutor.be.model.UpdateRequest;
////import online.ondemandtutor.be.repository.AuthenticationRepository;
////import online.ondemandtutor.be.service.AccountService;
////import online.ondemandtutor.be.service.AuthenticationService;
////import online.ondemandtutor.be.service.TokenService;
////import online.ondemandtutor.be.validation.LoginValidation;
////
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.MockitoAnnotations;
////import org.mockito.junit.jupiter.MockitoExtension;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.test.web.servlet.MockMvc;
////import org.springframework.test.web.servlet.setup.MockMvcBuilders;
////
////import static org.junit.jupiter.api.Assertions.assertEquals;
////import static org.mockito.Mockito.when;
////
////@ExtendWith(MockitoExtension.class)
////public class UpdateProfileTest {
////    @Mock
////    private AuthenticationManager authenticationManager;
////
////    @Mock
////    private TokenService tokenService;
////
////    @Mock
////    private AuthenticationRepository authenticationRepository;
////
////    @Mock
////    private AuthenticationService authenticationService;
////
////    @Mock
////    private AuthenticationAPI authenticationAPI;
////
////    @Mock
////    private AccountService accountService;
////
////    private MockMvc mockMvc;
////
////    @BeforeEach
////    public void setUp() {
////        // Setup mock behavior or reset state before each test method
////        MockitoAnnotations.initMocks(this);
////        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationAPI).build();
////
////    }
////
////    @Test
////    public void updateProfile(){
////        //Create a mock account
////        Account mockAccount = Account.builder()
////                .email("khai2@fpt.edu.vn")
////                .password("khai2")
////                .fullname("Phan Quang Khai")
////                .role(RoleEnum.STUDENT)
////                .phone("3928128379")
////                .isDeleted(false)
////                .build();
////
////        UpdateRequest updateRequest = new UpdateRequest();
////        updateRequest.setFullname("Phan Quang Khai");
////        updateRequest.setPhone("3928128379");
////
////        when(authenticationService.getCurrentAccount()).thenReturn(mockAccount);
////
//////        when(accountService.updateAccount(updateRequest)).thenReturn(mockAccount);
////
////        when(authenticationRepository.save(mockAccount)).thenReturn(mockAccount);
////
////        Account updatedAccount = accountService.updateAccount(updateRequest);
////
////        assertEquals(mockAccount, updatedAccount);
////    }
////
////
////}
//
//import online.ondemandtutor.be.exception.BadRequestException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//import online.ondemandtutor.be.entity.Account;
//import online.ondemandtutor.be.model.UpdateRequest;
//import online.ondemandtutor.be.repository.AuthenticationRepository;
//import online.ondemandtutor.be.service.AccountService;
//import online.ondemandtutor.be.service.AuthenticationService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class UpdateProfileTest {
//
//    @InjectMocks
//    private AccountService accountService;
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @Mock
//    private AuthenticationService authenticationService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void updateAccount_HappyCase() {
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setPhone("09217261");
//        updateRequest.setFullname("Khai Phan Quang");
//
//        Account existingAccount = new Account();
//        existingAccount.setPhone("3928128379");
//        existingAccount.setFullname("Phan Quang Khai");
//
//        when(authenticationService.getCurrentAccount()).thenReturn(existingAccount);
//        when(authenticationRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        Account result = accountService.updateAccount(updateRequest);
//
//        assertEquals(updateRequest.getPhone(), result.getPhone());
//        assertEquals(updateRequest.getFullname(), result.getFullname());
//    }
//
//    @Test
//    public void updateAccount_UnhappyCase() {
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setPhone("09217261");
//        updateRequest.setFullname("Khai Phan Quang");
//
//        when(authenticationService.getCurrentAccount()).thenReturn(null);
//
//        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
//            accountService.updateAccount(updateRequest);
//        });
//
//        assertEquals("Account is not found!", exception.getMessage());
//
//    }
//}