package com.miu.accounts.repository;

import com.miu.accounts.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> getUserByUsername(String username);
    Optional<Account> getAccountById(String id);
}
