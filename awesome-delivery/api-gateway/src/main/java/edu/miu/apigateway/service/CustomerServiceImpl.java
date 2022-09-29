package edu.miu.apigateway.service;

import edu.miu.apigateway.utils.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl {
    private static String url = "";

    @Value("${customers.orderBaseurl}")
    private String orderBaseUrl;

    @Value("${customers.searchBaseurl}")
    private String searchBaseUrl;

    public ResponseEntity<?> getRestaurants() {
        url = searchBaseUrl+"search";
        return Requests.restMethod(url, null, "get");
    }

    public ResponseEntity<?> getOrders(String cus_id) {
        url = orderBaseUrl+"api/orders?customerId="+cus_id;
        return Requests.restMethod(url, "", "get");
    }

    public ResponseEntity<?> createOrder(Object orderBody) {
        url = orderBaseUrl+"api/orders";
        return Requests.restMethod(url, orderBody, "post");
    }

    public ResponseEntity<?> filterRestaurant(String restaurant, String menu) {
        url = searchBaseUrl+"search?restaurant=";
        if(menu == null || menu.isBlank() || menu.isEmpty()){
            url = url+restaurant;
        }else{
            url = url+restaurant+"&menu="+menu;
        }
        return Requests.restMethod(url, null, "get");
    }
}
