package edu.miu.apigateway.controller;

import edu.miu.apigateway.constants.RestEndpoints;
import edu.miu.apigateway.service.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.DRIVER_PREFIX)
public class DriverController {

    private DriverServiceImpl driverService;
    @Autowired
    public DriverController(DriverServiceImpl driverService){
        this.driverService = driverService;
    }

    // Retrieves all ready orders
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping(RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS)
    public ResponseEntity<?> getOrders(@PathVariable String dri_id){
        System.out.println("Driver Id --- "+dri_id);
        return driverService.getOrders(dri_id);
    }

    // Picks order
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.PICK)
    public ResponseEntity<?> pickOrder(@PathVariable String dri_id, @PathVariable String ord_id){
        System.out.println("Driver Id --- "+dri_id);
        System.out.println("Order Id --- "+ord_id);
        return driverService.pickOrder(dri_id, ord_id);
    }

    // Deliver order
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @PutMapping(RestEndpoints.DRI_ID+RestEndpoints.DRI_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.DELIVER)
    public ResponseEntity<?> deliverOrder(@PathVariable String dri_id, @PathVariable String ord_id){
        System.out.println("Driver Id --- "+dri_id);
        System.out.println("Order Id --- "+ord_id);
        return driverService.deliverOrder(dri_id, ord_id);
    }
}
