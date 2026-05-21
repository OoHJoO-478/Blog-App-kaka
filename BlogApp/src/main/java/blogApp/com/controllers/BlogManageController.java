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

@Controller
public class BlogManageController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BlogServices blogServices;

    @GetMapping("/blog-manage")
    public String getBlogManagePage(Model model) {
        Account account = (Account) session.getAttribute("loginAccountInfo");

        if (account == null) {
            return "redirect:/login";
        }

        List<Blog> blogList = blogServices.selectAllBlogList(account.getAccountId());

        model.addAttribute("accountName", account.getAccountName());
        model.addAttribute("blogList", blogList);

        return "blog-manage";
    }
}