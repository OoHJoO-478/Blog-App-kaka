package blogApp.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blogApp.com.models.entity.Account;
import blogApp.com.models.entity.Blog;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;

//ブログ記事の管理画面を表示するコントローラー
@Controller
public class BlogManageController {
	// ログイン中のユーザー情報を確認するためのセッション
    @Autowired
    private HttpSession session;
    // ブログ記事の取得処理を行うサービスクラス
    @Autowired
    private BlogServices blogServices;
    
    // 記事ステータス管理画面を表示する処理
    // URL：http://localhost:8080/blog-manage
    @GetMapping("/blog-manage")
    public String getBlogManagePage(Model model) {
    	// セッションからログイン中のアカウント情報を取得する
        Account account = (Account) session.getAttribute("loginAccountInfo");
        // ログインしていない場合は、ログイン画面へリダイレクトする
        if (account == null) {
            return "redirect:/login";
        }
        // ログイン中のユーザーIDを使って、そのユーザーの記事一覧を取得する
        List<Blog> blogList = blogServices.selectAllBlogList(account.getAccountId());

        model.addAttribute("accountName", account.getAccountName());
        model.addAttribute("blogList", blogList);
        // blog-manage.html を表示する
        return "blog-manage";
    }
}