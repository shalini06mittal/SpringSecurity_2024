package com.oauth.demo.SpringBootOauthDemo.repository;


import com.oauth.demo.SpringBootOauthDemo.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(long customerId);

}
