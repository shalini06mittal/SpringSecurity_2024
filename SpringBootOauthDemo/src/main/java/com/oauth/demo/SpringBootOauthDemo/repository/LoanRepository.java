package com.oauth.demo.SpringBootOauthDemo.repository;


import com.oauth.demo.SpringBootOauthDemo.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface 	LoanRepository extends CrudRepository<Loans, Long> {

	// @PreAuthorize("hasRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);

}
