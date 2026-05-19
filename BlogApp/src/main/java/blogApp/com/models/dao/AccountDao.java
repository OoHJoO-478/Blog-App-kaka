package blogApp.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blogApp.com.models.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	Account save(Account account);
	
	Account findByAccountEmail(String accountEmail);
	
	Account findByAccountEmailAndPassword(String accountEmail,String password);
	

}
