package edu.miu.apigateway.service;

import edu.miu.apigateway.utils.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl {
    private static String url = "";

    @Value("${restaurants.baseurl}")
    private String baseUrl;

    @Value("${restaurants.restaurant}")
    private String restaurants;

    public ResponseEntity<?> getMenus(String res_id) {
        url = baseUrl+restaurants+"/"+res_id+"/menus";
        return Requests.restMethod(url, "", "get");
    }

    public ResponseEntity<?> addMenu(String res_id, Object menuBody) {
        url = baseUrl+restaurants+"/"+res_id+"/menus";
        return Requests.restMethod(url, menuBody, "post");
    }

    public ResponseEntity<?> updateMenu(String res_id, String menu_id, Object menuBody) {
        url = baseUrl+restaurants+"/"+res_id+"/menus/"+menu_id;
        return Requests.restMethod(url, menuBody, "put");
    }

    public ResponseEntity<?> deleteMenu(String res_id, String menu_id) {
        url = baseUrl+restaurants+"/"+res_id+"/menus/"+menu_id;
        return Requests.restMethod(url, null, "delete");
    }

    public ResponseEntity<?> getOrders(String res_id) {
        url = baseUrl+restaurants+"/"+res_id+"/orders";
        return Requests.restMethod(url, null, "get");
    }

    public ResponseEntity<?> acceptOrder(String res_id, String ord_id) {
        url = baseUrl+restaurants+"/"+res_id+"/orders/"+ord_id+"/accept";
        return Requests.restMethod(url, null, "post");
    }

    public ResponseEntity<?> rejectOrder(String res_id, String ord_id) {
        url = baseUrl+restaurants+"/"+res_id+"/orders/"+ord_id+"/reject";
        return Requests.restMethod(url, null, "post");
    }

    public ResponseEntity<?> readyOrder(String res_id, String ord_id) {
        url = baseUrl+restaurants+"/"+res_id+"/orders/"+ord_id+"/ready";
        return Requests.restMethod(url, null, "post");
    }
}
