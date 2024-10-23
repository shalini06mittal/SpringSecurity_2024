package com.security.demo.SpringSecurityDemoLatest.repository;

import java.util.List;

import com.security.demo.SpringSecurityDemoLatest.model.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, String> {
	
	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(long customerId);

}
