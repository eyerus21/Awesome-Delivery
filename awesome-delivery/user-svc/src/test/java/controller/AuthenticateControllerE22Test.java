package controller;

import edu.miu.delivery.usersvc.authentication.JWTTokenProvider;
import edu.miu.delivery.usersvc.services.UserService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticateControllerE22Test
{

    @InjectMocks
    AuthenticationManager authenticationManager;

    @InjectMocks
    JWTTokenProvider tokenProvider;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
}
