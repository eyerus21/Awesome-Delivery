package com.miu.awsomedelivery.driversvc.repository;

import com.miu.awsomedelivery.driversvc.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends MongoRepository<Driver,String> {

}
