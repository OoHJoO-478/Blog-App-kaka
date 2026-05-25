package blogApp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blogApp.com.models.entity.Account;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;

//ブログ記事の新規投稿機能を管理するコントローラー
@Controller
public class BlogRegisterController {
	// ブログ記事の登録処理を行うサービスクラス
    @Autowired
    private BlogServices blogServices;
    // ログイン中のユーザー情報を確認するためのセッション
    @Autowired
    private HttpSession session;
    // ブログ投稿画面を表示する処理
    // URL：http://localhost:8080/blog-form
    @GetMapping("/blog-form")
    public String getBlogRegisterPage(Model model) {
    	// セッションからログイン中のアカウント情報を取得する
        Account account = (Account) session.getAttribute("loginAccountInfo");
        // ログインしていない場合は、ログイン画面へリダイレクトする
        if (account == null) {
            return "redirect:/login";
        }
        // 画面に表示するため、ログイン中のユーザー名を渡す
        model.addAttribute("accountName", account.getAccountName());
        return "blog-form";
    }
    // ブログ投稿フォームから送信された内容を受け取り、新しい記事を登録する処理
    @PostMapping("/blog/register/process")
    public String blogRegisterProcess(
            @RequestParam String blogTitle,
            @RequestParam String categoryName,
            @RequestParam String article,
            @RequestParam("blogImageFile") MultipartFile blogImageFile) {
    	// セッションからログイン中のアカウント情報を取得する
        Account account = (Account) session.getAttribute("loginAccountInfo");
        // ログインしていない場合は、ログイン画面へリダイレクトする
        if (account == null) {
            return "redirect:/login";
        }
        // データベースに保存する画像ファイル名
        // 画像が選択されていない場合は null のままにする
        String fileName = null;
        // 画像ファイルが選択されている場合のみ、画像を保存する
        if (!blogImageFile.isEmpty()) {
        	// 同じファイル名で上書きされないように、
            // 現在時刻をファイル名の先頭につける
            fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-")
                    .format(new Date()) + blogImageFile.getOriginalFilename();

            try {
            	// アップロードされた画像を static/img フォルダに保存する
                Files.copy(
                        blogImageFile.getInputStream(),
                        Path.of("src/main/resources/static/img/" + fileName)
                );
            } catch (IOException e) {
            	// 画像保存中にエラーが発生した場合、エラー内容を表示する
                e.printStackTrace();
            }
        }
        // 入力された内容と画像ファイル名を使って、新しいブログ記事を登録する
        blogServices.createBlog(
                blogTitle,
                categoryName,
                fileName,
                article,
                account.getAccountId()
        );
        // 登録後、ブログ一覧画面へリダイレクトする
        return "redirect:/blog-list";
    }
}