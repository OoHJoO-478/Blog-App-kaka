package blogApp.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blogApp.com.models.entity.Account;
import blogApp.com.models.entity.Blog;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;
//ブログ記事の編集機能を管理するコントローラー
@Controller
public class BlogEditController {
	// ログイン中のユーザー情報を確認するためのセッション
    @Autowired
    private HttpSession session;
    
    // ブログ記事の取得・更新処理を行うサービスクラス
    @Autowired
    private BlogServices blogServices;
    // ブログ編集画面を表示する処理
    // URL例：http://localhost:8080/blog-edit?blogId=1
    @GetMapping("/blog-edit")
    public String getBlogEditPage(@RequestParam Long blogId, Model model) {
    	// セッションからログイン中のアカウント情報を取得する
        Account account = (Account) session.getAttribute("loginAccountInfo");
        
        // ログインしていない場合は、ログイン画面へリダイレクトする
        if (account == null) {
            return "redirect:/login";
        }
        // blogId を使って、編集対象の記事をデータベースから取得する
        Blog blog = blogServices.findBlogById(blogId);
        // 該当する記事が存在しない場合は、ブログ一覧画面へ戻す
        if (blog == null) {
            return "redirect:/blog-list";
        }
        // 画面に表示するため、ログイン中のユーザー名を渡す
        model.addAttribute("accountName", account.getAccountName());
        // 画面に表示するため、編集対象の記事情報を渡す
        model.addAttribute("blog", blog);
        // blog-edit.html を表示する
        return "blog-edit";
    }
    // ブログ編集画面から送信された内容を受け取り、記事を更新する処理
    @PostMapping("/blog-edit")
    public String updateBlog(
            @RequestParam Long blogId,
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
        // 更新対象の記事をデータベースから取得する
        Blog blog = blogServices.findBlogById(blogId);
        // 記事が存在しない場合は、ブログ一覧画面へ戻す
        if (blog == null) {
            return "redirect:/blog-list";
        }
        // 既存の画像ファイル名を一旦保持する
        // 新しい画像がアップロードされなかった場合は、この画像をそのまま使う
        String fileName = blog.getBlogImage();
        // 新しい画像ファイルが選択されている場合のみ、画像を保存する
        if (!blogImageFile.isEmpty()) {
        	// 画像ファイル名が重複しないように、現在時刻をファイル名の先頭につける
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
        // 入力された内容でブログ記事を更新する
        blogServices.updateBlog(
                blogId,
                blogTitle,
                categoryName,
                fileName,
                article,
                account.getAccountId()
        );
        // 更新後、ブログ一覧画面へリダイレクトする
        return "redirect:/blog-manage";
    }
}