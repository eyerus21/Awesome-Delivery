package edu.miu.delivery.usersvc.services;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.miu.delivery.usersvc.dto.SignupRequest;
import edu.miu.delivery.usersvc.model.Customer;
import edu.miu.delivery.usersvc.model.Driver;
import edu.miu.delivery.usersvc.model.Restaurant;
import edu.miu.delivery.usersvc.model.User;
import edu.miu.delivery.usersvc.model.User.Role;
import edu.miu.delivery.usersvc.model.User.UserType;
import edu.miu.delivery.usersvc.repository.CustomerRepository;
import edu.miu.delivery.usersvc.repository.DriverRepository;
import edu.miu.delivery.usersvc.repository.RestaurantRepository;
import edu.miu.delivery.usersvc.repository.UserRepository;
import edu.miu.delivery.usersvc.util.UserUtil;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository resturantRepository;

    @Autowired
    private DriverRepository driveRepository;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${app.topic-name}")
    String userTopic;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User client = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User NOT Found"));

        return UserUtil.userToPrincipal(client);
    }

    public Customer addCustomer(SignupRequest request) throws JsonProcessingException {
        Customer c = new Customer();
        c.setRoles(Stream.of(Role.CUSTOMER).collect(Collectors.toSet()));
        c.setUserType(UserType.CUSTOMER);
        c.setUsername(request.getUsername());
        c.setPassword(request.getPassword());
        c.setName(request.getName());
        c.setPhoneNumber(request.getPhoneNumber());
        c.setEmail(request.getEmail());
        c.setAddress(request.getAddress());
        c.setPayment(request.getPayment());
        
        Customer customer = this.customerRepository.save(c);
        kafkaTemplate.send(userTopic, customer.getId(), objectMapper.writeValueAsString(customer));
        return customer;
    }

    public Restaurant addResturant(SignupRequest request) throws JsonProcessingException {
        Restaurant r = new Restaurant();
        r.setRoles(Stream.of(Role.RESTAURANT).collect(Collectors.toSet()));
        r.setUserType(UserType.RESTAURANT);
        r.setUsername(request.getUsername());
        r.setPassword(request.getPassword());
        r.setName(request.getName());
        r.setPhoneNumber(request.getPhoneNumber());
        r.setEmail(request.getEmail());
        r.setAddress(request.getAddress());
        
        Restaurant restaurant = this.resturantRepository.save(r);
        kafkaTemplate.send(userTopic, restaurant.getId(), objectMapper.writeValueAsString(restaurant));
        return restaurant;
    }

    public Driver addDriver(SignupRequest request) throws JsonProcessingException {
        Driver d = new Driver();
        d.setRoles(Stream.of(Role.DRIVER).collect(Collectors.toSet()));
        d.setUserType(UserType.DRIVER);
        d.setUsername(request.getUsername());
        d.setPassword(request.getPassword());
        d.setName(request.getName());
        d.setPhoneNumber(request.getPhoneNumber());
        d.setEmail(request.getEmail());
        d.setAddress(request.getAddress());

        Driver driver = this.driveRepository.save(d);
        kafkaTemplate.send(userTopic, driver.getId(), objectMapper.writeValueAsString(driver));
        return driver;
    }
}
