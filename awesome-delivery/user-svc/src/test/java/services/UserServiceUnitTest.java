package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.delivery.usersvc.authentication.UserDetail;
import edu.miu.delivery.usersvc.model.Customer;
import edu.miu.delivery.usersvc.model.User;
import edu.miu.delivery.usersvc.repository.CustomerRepository;
import edu.miu.delivery.usersvc.repository.DriverRepository;
import edu.miu.delivery.usersvc.repository.RestaurantRepository;
import edu.miu.delivery.usersvc.repository.UserRepository;
import edu.miu.delivery.usersvc.util.UserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;
//
//    @InjectMocks
//    private CustomerRepository customerRepository;
//
//    @InjectMocks
//    private RestaurantRepository resturantRepository;
//
//    @InjectMocks
//    private DriverRepository driveRepository;
//
//    @InjectMocks
//    KafkaTemplate<String, Object> kafkaTemplate;
//
//    @InjectMocks
//    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
       MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameWhenUserNameIsGiven() {

        String username = "sabbir";

        UserDetail expected = new UserDetail();
        expected.setUsername(username);
        User customer = new Customer();
        customer.setName(username);

        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(customer));

        Optional<User> client = userRepository.findByUsername(username);

        UserDetail result = UserUtil.userToPrincipal(client.get());

        Assert.assertEquals(expected.getUsername().toLowerCase(), result.getUsername().toLowerCase());
    }
}
