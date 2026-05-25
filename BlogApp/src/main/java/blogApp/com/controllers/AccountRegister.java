package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blogApp.com.services.AccountServices;

@Controller
public class AccountRegister {
	// アカウント登録処理を行うサービスクラスを使用する
	@Autowired
	private AccountServices accountServices;
	
	// 会員登録画面を表示する
    // URL：http://localhost:8080/register
	@GetMapping("/register")
	public String getAccountRegisterPage() {
		return "register.html";
	}
	// 会員登録フォームから送信された情報を受け取る
	@PostMapping("/register/process")
	public String accountRegisterProcess(
	        @RequestParam String accountName,
	        @RequestParam String accountEmail,
	        @RequestParam String password) {
		
		// 入力された名前・メールアドレス・パスワードを使って新規アカウントを作成する
	    if (accountServices.createAccount(accountEmail, accountName, password)) {
	    	// 登録成功時はログイン画面へ遷移する
	        return "login";
	    } else {
	    	// 登録失敗時は会員登録画面へ戻る
	        return "register";
	    }
	}

}
