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
	// アカウント情報の処理を行うサービスクラスを使用する
	@Autowired
	private AccountServices accountServices;
	
	// ログイン状態を保存するためのセッション
	@Autowired
	private HttpSession session;
	  
	// ログイン画面を表示する
    // URL：http://localhost:8080/login
	@GetMapping("/login")
	public String getAccountRegisterPage() {
		return "login";
	}
	// ログインフォームから送信されたメールアドレスとパスワードを受け取る
	@PostMapping("/login/process")
	public String accountLoginProcess(@RequestParam String accountEmail,
	        @RequestParam String password) {
		// 入力されたメールアドレスとパスワードでアカウントを検索する
		Account account = accountServices.loginCheck(accountEmail, password);
		// アカウントが存在しない場合、ログイン画面に戻る
		if(account == null) {
			return "login";
		// アカウントが存在する場合、ログイン成功
		}else {
			// ログインしたユーザー情報をセッションに保存する
			session.setAttribute("loginAccountInfo", account);
			// ブログ一覧画面へリダイレクトする
			return "redirect:/blog-list";
		}
		
	}
}
