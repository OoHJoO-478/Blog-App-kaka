package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

// ログアウト機能を管理するコントローラー
@Controller
public class AccountLogoutController {

    // ログイン状態を管理するためのセッション
    @Autowired
    private HttpSession session;

    // ログアウト処理を行う
    // URL：http://localhost:8080/blog/logout
    @GetMapping("/blog/logout")
    public String blogLogout() {

        // セッション情報をすべて削除する
        // これにより、ログイン状態が解除される
        session.invalidate();

        // ログアウト後、ログイン画面へリダイレクトする
        return "redirect:/login";
    }
}