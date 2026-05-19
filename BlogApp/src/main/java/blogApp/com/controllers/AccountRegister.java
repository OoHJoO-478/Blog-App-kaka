package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blogApp.com.services.AccountServices;

@Controller
public class AccountRegister {
	@Autowired
	private AccountServices accountServices;
	
	@GetMapping("/register")
	public String getAccountRegisterPage() {
		return "register.html";
	}
	
	@GetMapping("/register/process")
	public String accountRegisterProcess(@RequestParam String accountName,
			@RequestParam String accountEmai,@RequestParam String password) {
		if(accountServices.createAccount(accountEmai, accountName, password)) {
			return "login.html";
			
		}else {
			return "register.html";
		}
	}

}
