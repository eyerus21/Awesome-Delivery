package miu.awesomedelivery.restaurantservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import miu.awesomedelivery.restaurantservice.model.Menu;
import miu.awesomedelivery.restaurantservice.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }
    @GetMapping("/{id}/menus")
    public List<Menu> getAllMenusOfRestaurant(@PathVariable   String id) {
        return  menuService.getAllMenusOfRestaurant(id);
    }
    @PostMapping("/{id}/addMenu")
    public Menu addMenu(@PathVariable String id, @RequestBody Menu menu) throws JsonProcessingException {
        return menuService.addMenu(id,menu);
    }

    @PutMapping("/{restId}/updateMenu/{menuId}")
    public Menu updateMenu(@PathVariable String restId, @PathVariable String menuId, @RequestBody Menu menu) throws Exception {
        return menuService.updateMenu(restId, menuId, menu);
    }

    @DeleteMapping("/{restId}/deleteMenu/{menuId}")
    public String  deleteMenu(@PathVariable String restId, @PathVariable String menuId) throws Exception {
        return menuService.deleteMenu(restId, menuId);
    }
}
