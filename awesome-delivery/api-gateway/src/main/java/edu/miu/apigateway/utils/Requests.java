package edu.miu.apigateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Requests {
    private static RestTemplate restTemplate = new RestTemplate();

    public static ResponseEntity<?> restMethod(String url, Object body, String type){
        try{
            HttpEntity<Object> request;
            Object response;
            if(type.equals("get")){
                response = restTemplate.getForEntity(url,Object.class).getBody();
            }else if(type.equals("post")){
                request = new HttpEntity<>(body);
                response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class).getBody();
            }else if(type.equals("put")){
                request = new HttpEntity<>(body);
                response = restTemplate.exchange(url, HttpMethod.PUT, request, Object.class).getBody();
            }else{
                request = new HttpEntity<>(body);
                response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            }
            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            String message = "Bad request!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
