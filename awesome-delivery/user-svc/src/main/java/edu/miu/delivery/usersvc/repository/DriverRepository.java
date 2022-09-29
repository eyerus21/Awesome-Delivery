package edu.miu.delivery.usersvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.miu.delivery.usersvc.model.Driver;

import java.util.Optional;

@Repository
public interface DriverRepository extends CrudRepository<Driver, String> {

}
