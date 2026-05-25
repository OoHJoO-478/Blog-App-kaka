package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import blogApp.com.models.entity.Blog;
import blogApp.com.models.entity.Account;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;

//ブログ一覧画面を表示するコントローラー
@Controller
public class BlogListController {
	// ログイン中のユーザー情報を確認するためのセッション
	@Autowired
	private HttpSession session;
	// ブログ記事の取得処理を行うサービスクラス
	@Autowired
	private BlogServices blogServices;
	
	// ブログ一覧画面を表示する処理
    // URL：http://localhost:8080/blog-list
	@GetMapping("/blog-list")
	public String getBlogList(Model model) {
		// セッションからログイン中のアカウント情報を取得する
		Account account = (Account)session.getAttribute("loginAccountInfo");
		// ログインしていない場合は、ログイン画面へリダイレクトする
		if(account == null) {
			return "redirect:/login";
			
		}else {
			// ログイン中のユーザーIDを使って、そのユーザーの記事一覧を取得する
			List<Blog>BlogList = blogServices.selectAllBlogList(account.getAccountId());
			// 画面に表示するため、ユーザー名を渡す
			model.addAttribute("accountName",account.getAccountName());
			// 画面に表示するため、ブログ記事一覧を渡す
			model.addAttribute("blogList",BlogList);
			// blog-list.html を表示する
			return "blog-list";
		}
	}
}
