package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blogApp.com.models.entity.Account;
import blogApp.com.services.AccountServices;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountLoginController {
	@Autowired
	private AccountServices accountServices;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/login")
	public String getAccountRegisterPage() {
		return "login";
	}
	
	@PostMapping("/login/process")
	public String accountLoginProcess(@RequestParam String accountEmail,
	        @RequestParam String password) {
		Account account = accountServices.loginCheck(accountEmail, password);
		if(account == null) {
			return "login";
		}else {
			session.setAttribute("loginAccountInfo", account);
			return "redirect:/blog-list";
		}
		
	}
	
	

}
