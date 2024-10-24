package com.security.demo.SpringSecurityDemoLatest.repository;

import java.util.List;

import com.security.demo.SpringSecurityDemoLatest.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

//	@PostAuthorize("hasAnyRole('ROOT')")
//	@PreAuthorize("hasAnyRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);

}
