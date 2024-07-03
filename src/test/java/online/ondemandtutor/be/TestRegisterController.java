//import online.ondemandtutor.be.repository.AuthenticationRepository;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@ExtendWith(MockitoExtension.class)
//public class TestRegisterController {
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private RegisterValidation registerValidation;
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
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationAPI).build();
//    }
//
//    @Test
//    public void testRegister_Successful() {
//        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
//        registerRequest.setConfirmPassword("ValidPassword123!");
//
//        Account mockAccount = new Account("john@example.com", "hashedPassword", "John Doe", "123456789", RoleEnum.USER, false);
//
//        when(authenticationRepository.findAccountByEmail(registerRequest.getEmail())).thenReturn(null);
//        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedPassword");
//        when(authenticationRepository.save(any(Account.class))).thenReturn(mockAccount);
//
//        AccountResponse response = authenticationService.register(registerRequest);
//
//        assertNotNull(response);
//        assertEquals(mockAccount.getEmail(), response.getEmail());
//        assertEquals(mockAccount.getFullname(), response.getFullname());
//        assertEquals(mockAccount.getRole(), response.getRole());
//        assertEquals(mockAccount.getPhone(), response.getPhone());
//
//        verify(authenticationRepository).findAccountByEmail(registerRequest.getEmail());
//        verify(passwordEncoder).encode(registerRequest.getPassword());
//        verify(authenticationRepository).save(any(Account.class));
//    }
//
//    @Test
//    public void testRegister_EmailAlreadyExists() {
//        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
//        registerRequest.setConfirmPassword("ValidPassword123!");
//
//        Account existingAccount = new Account();
//        existingAccount.setEmail(registerRequest.getEmail());
//
//        when(authenticationRepository.findAccountByEmail(registerRequest.getEmail())).thenReturn(existingAccount);
//
//        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
//            authenticationService.register(registerRequest);
//        });
//
//        assertEquals("Email is already in use", exception.getMessage());
//
//        verify(authenticationRepository).findAccountByEmail(registerRequest.getEmail());
//        verifyNoMoreInteractions(passwordEncoder);
//        verifyNoMoreInteractions(authenticationRepository);
//    }
//
//    @Test
//    public void testRegister_InvalidEmail() {
//        String invalidEmail = "invalid_email";
//        assertFalse(registerValidation.isValidEmail(invalidEmail));
//
//        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", invalidEmail);
//        registerRequest.setConfirmPassword("ValidPassword123!");
//
//        Exception exception = assertThrows(InvalidFieldException.class, () -> {
//            authenticationService.register(registerRequest);
//        });
//
//        assertEquals("Email is not valid", exception.getMessage());
//
//        verifyNoInteractions(passwordEncoder);
//        verifyNoInteractions(authenticationRepository);
//    }
//
//    @Test
//    public void testRegister_InvalidPassword() {
//        String validEmail = "john@example.com";
//        String invalidPassword = "short";
//        assertFalse(registerValidation.isValidPassword(invalidPassword));
//
//        RegisterRequest registerRequest = new RegisterRequest("John Doe", invalidPassword, "123456789", validEmail);
//        registerRequest.setConfirmPassword(invalidPassword);
//
//        Exception exception = assertThrows(InvalidFieldException.class, () -> {
//            authenticationService.register(registerRequest);
//        });
//
//        assertEquals("Password must be at least 8 characters", exception.getMessage());
//
//        verifyNoInteractions(passwordEncoder);
//        verifyNoInteractions(authenticationRepository);
//    }
//
//    @Test
//    public void testRegister_PasswordMismatch() {
//        RegisterRequest registerRequest = new RegisterRequest("John Doe", "ValidPassword123!", "123456789", "john@example.com");
//        registerRequest.setConfirmPassword("DifferentPassword123!");
//
//        Exception exception = assertThrows(InvalidFieldException.class, () -> {
//            authenticationService.register(registerRequest);
//        });
//
//        assertEquals("Password and confirm password must match", exception.getMessage());
//
//        verifyNoInteractions(passwordEncoder);
//        verifyNoInteractions(authenticationRepository);
//    }
//}
