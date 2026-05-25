package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blogApp.com.models.entity.Account;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;
//ブログ記事の削除機能を管理するコントローラー
@Controller
public class BlogDeleteController {
	
	// ログイン中のユーザー情報を確認するためのセッション
    @Autowired
    private HttpSession session;
    
    // ブログ記事の処理を行うサービスクラス
    @Autowired
    private BlogServices blogServices;

    // ブログ記事を削除する処理
    // 管理画面の「削除」ボタンから送信される
    @PostMapping("/blog-delete")
    public String deleteBlog(@RequestParam Long blogId) {
    	
    	// セッションからログイン中のアカウント情報を取得する
        Account account = (Account) session.getAttribute("loginAccountInfo");
        
        // ログインしていない場合は、ログイン画面へ戻す
        if (account == null) {
            return "redirect:/login";
        }
        
        // 指定された blogId の記事を削除する
        blogServices.deleteBlog(blogId);
        // 削除後、記事管理画面へリダイレクトする
        return "redirect:/blog-manage";
    }
}