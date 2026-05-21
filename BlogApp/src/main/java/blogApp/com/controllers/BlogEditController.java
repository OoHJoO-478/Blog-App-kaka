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

@Controller
public class BlogEditController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BlogServices blogServices;

    @GetMapping("/blog-edit")
    public String getBlogEditPage(@RequestParam Long blogId, Model model) {
        Account account = (Account) session.getAttribute("loginAccountInfo");

        if (account == null) {
            return "redirect:/login";
        }

        Blog blog = blogServices.findBlogById(blogId);

        if (blog == null) {
            return "redirect:/blog-list";
        }

        model.addAttribute("accountName", account.getAccountName());
        model.addAttribute("blog", blog);

        return "blog-edit";
    }

    @PostMapping("/blog-edit")
    public String updateBlog(
            @RequestParam Long blogId,
            @RequestParam String blogTitle,
            @RequestParam String categoryName,
            @RequestParam String article,
            @RequestParam("blogImageFile") MultipartFile blogImageFile) {

        Account account = (Account) session.getAttribute("loginAccountInfo");

        if (account == null) {
            return "redirect:/login";
        }

        Blog blog = blogServices.findBlogById(blogId);

        if (blog == null) {
            return "redirect:/blog-list";
        }

        String fileName = blog.getBlogImage();

        if (!blogImageFile.isEmpty()) {
            fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-")
                    .format(new Date()) + blogImageFile.getOriginalFilename();

            try {
                Files.copy(
                        blogImageFile.getInputStream(),
                        Path.of("src/main/resources/static/img/" + fileName)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        blogServices.updateBlog(
                blogId,
                blogTitle,
                categoryName,
                fileName,
                article,
                account.getAccountId()
        );

        return "redirect:/blog-list";
    }
}