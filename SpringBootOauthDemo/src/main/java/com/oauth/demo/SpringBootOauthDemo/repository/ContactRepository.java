package com.oauth.demo.SpringBootOauthDemo.repository;


import com.oauth.demo.SpringBootOauthDemo.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}
