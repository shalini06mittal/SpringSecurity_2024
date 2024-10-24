package com.oauth.demo.SpringBootOauthDemo.repository;



import com.oauth.demo.SpringBootOauthDemo.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    Accounts findByCustomerId(long customerId);

}
