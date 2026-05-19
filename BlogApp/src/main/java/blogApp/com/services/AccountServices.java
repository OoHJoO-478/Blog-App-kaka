package blogApp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogApp.com.models.dao.AccountDao;
import blogApp.com.models.entity.Account;

@Service
public class AccountServices {
	@Autowired
	private AccountDao accountDao;
	
	public boolean createAccount(String accountEmail,String accountName,String password) {
		if(accountDao.findByAccountEmail(accountEmail)==null) {
			
			accountDao.save(new Account(accountEmail,accountName,password));
			return true;
		}else {
			return false;
		}
	}
	
	public Account loginCheck(String accountEmail,String password) {
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if(account == null) {
			return null;
		}else {
			return account;
		}
	}
}
