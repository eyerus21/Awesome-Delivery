package edu.miu.apigateway.service;

import edu.miu.apigateway.utils.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl {
    private static String url = "";

    @Value("${drivers.baseurl}")
    private String baseUrl;

    @Value("${drivers.driver}")
    private String drivers;

    public ResponseEntity<?> getOrders(String dri_id) {
        url = baseUrl+drivers+"/"+dri_id+"/orders";
        return Requests.restMethod(url, "", "get");
    }

    public ResponseEntity<?> pickOrder(String dri_id, String ord_id) {
        url = baseUrl+drivers+"/"+dri_id+"/orders/"+ord_id+"/pickOrder";
        return Requests.restMethod(url, null, "post");
    }

    public ResponseEntity<?> deliverOrder(String dri_id, String ord_id) {
        url = baseUrl+drivers+"/"+dri_id+"/orders/"+ord_id+"/deliverOrder";
        return Requests.restMethod(url, null, "post");
    }
}
