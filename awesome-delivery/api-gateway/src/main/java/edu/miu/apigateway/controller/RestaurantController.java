package edu.miu.apigateway.controller;

import edu.miu.apigateway.constants.RestEndpoints;
import edu.miu.apigateway.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.RESTAURANT_PREFIX)
public class RestaurantController {

    private RestaurantServiceImpl restaurantService;
    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantService){
        this.restaurantService = restaurantService;
    }

    // Retrieves all menus of a restaurant
    @GetMapping(RestEndpoints.RES_ID+RestEndpoints.MENUS)
    public ResponseEntity<?> getMenus(@PathVariable String res_id){
        System.out.println("Restaurant Id --- "+res_id);
        return restaurantService.getMenus(res_id);
    }

    // Add menu to a restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @PostMapping(RestEndpoints.RES_ID+RestEndpoints.MENUS)
    public ResponseEntity<?> addMenu(@PathVariable String res_id,@RequestBody Object menuBody){
        System.out.println("Restaurant id --- "+res_id);
        System.out.println("Menu body --- "+menuBody);
        return restaurantService.addMenu(res_id, menuBody);
    }

    // Update a specific menu of a restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @PutMapping(RestEndpoints.RES_ID+RestEndpoints.MENUS+RestEndpoints.MENU_ID)
    public ResponseEntity<?> updateMenu(@PathVariable String res_id,@PathVariable String menu_id,@RequestBody Object menuBody){
        System.out.println("Res id --- "+res_id);
        System.out.println("Menu id to be updated --- "+menu_id);
        System.out.println("Menu body to be updated --- "+menuBody);
        return restaurantService.updateMenu(res_id, menu_id,menuBody);
    }

    // Delete a specific menu of a restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @DeleteMapping(RestEndpoints.RES_ID+RestEndpoints.MENUS+RestEndpoints.MENU_ID)
    public ResponseEntity<?> deleteMenu(@PathVariable String res_id,@PathVariable String menu_id){
        System.out.println("Restaurant id to be deleted --- "+res_id);
        System.out.println("Menu id to be deleted --- "+menu_id);
        return restaurantService.deleteMenu(res_id, menu_id);
    }

    // Retrieves all orders of a specific restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @GetMapping(RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS)
    public ResponseEntity<?> getOrders(@PathVariable String res_id){
        System.out.println("Restaurant Id --- "+res_id);
        return restaurantService.getOrders(res_id);
    }

    // Accept a specific order of a specific restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @PutMapping(RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.ACCEPT)
    public ResponseEntity<?> acceptOrder(@PathVariable String res_id,@PathVariable String ord_id){
        System.out.println("Res id --- "+res_id);
        System.out.println("Order id to be updated --- "+ord_id);
        return restaurantService.acceptOrder(res_id, ord_id);
    }

    // Reject a specific order of a specific restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @PutMapping(RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.REJECT)
    public ResponseEntity<?> rejectOrder(@PathVariable String res_id,@PathVariable String ord_id){
        System.out.println("Res id --- "+res_id);
        System.out.println("Order id to be updated --- "+ord_id);
        return restaurantService.rejectOrder(res_id, ord_id);
    }

    // Make Ready a specific order of a specific restaurant
    @PreAuthorize("hasAuthority('ROLE_RESTAURANT')")
    @PutMapping(RestEndpoints.RES_ID+RestEndpoints.RES_ORDERS+RestEndpoints.ORD_ID+RestEndpoints.READY)
    public ResponseEntity<?> readyOrder(@PathVariable String res_id,@PathVariable String ord_id){
        System.out.println("Res id --- "+res_id);
        System.out.println("Order id to be updated --- "+ord_id);
        return restaurantService.readyOrder(res_id, ord_id);
    }


}
