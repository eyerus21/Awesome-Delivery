package com.miu.awsomedelivery.driversvc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miu.awsomedelivery.driversvc.dto.PickOrderRequest;
import com.miu.awsomedelivery.driversvc.model.Driver;
import com.miu.awsomedelivery.driversvc.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface DriverService {

    Driver createDriver(Driver driver);
    Driver getDriverByID(String id);
    Boolean pickOrder(String driverID,String orderID) throws Exception;
    Boolean deliverOrder(String orderID) throws JsonProcessingException;
}
