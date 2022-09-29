package edu.miu.apigateway.controller;

import edu.miu.apigateway.constants.RestEndpoints;
import edu.miu.apigateway.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.CUSTOMER_PREFIX)
public class CustomerController {

    private CustomerServiceImpl customerService;
    @Autowired
    public CustomerController(CustomerServiceImpl customerService){
        this.customerService = customerService;
    }

    // Retrieves all restaurants of a restaurant
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping(RestEndpoints.RESTAURANTS)
    public ResponseEntity<?> getRestaurants(){
        System.out.println("Here are all the restaurants --- ");
        return customerService.getRestaurants();
    }

    // Retrieves all orders of a specific customer
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping(RestEndpoints.CUS_ID+RestEndpoints.CUS_ORDERS)
    public ResponseEntity<?> getOrders(@PathVariable String cus_id){
        System.out.println("Customer Id --- "+cus_id);
        return customerService.getOrders(cus_id);
    }

    // Creates/Places an order for a specific customer
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping(RestEndpoints.CUS_ID+RestEndpoints.CUS_ORDERS)
    public ResponseEntity<?> createOrder(@RequestBody Object orderBody){
        System.out.println("Order Body --- "+orderBody);
        return customerService.createOrder(orderBody);
    }

    // Search restaurants with a restaurant and menu
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping(RestEndpoints.RESTAURANTS+RestEndpoints.SEARCH)
    public ResponseEntity<?> filterRestaurants(@RequestParam(required=false) String restaurant,@RequestParam(required=false) String menu){
        System.out.println("Restaurant name --- "+restaurant);
        System.out.println("Menu name --- "+menu);
        return customerService.filterRestaurant(restaurant, menu);
    }

}
