package blogApp.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blogApp.com.models.entity.Account;
import blogApp.com.services.BlogServices;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BlogServices blogServices;

    @PostMapping("/blog-delete")
    public String deleteBlog(@RequestParam Long blogId) {
        Account account = (Account) session.getAttribute("loginAccountInfo");

        if (account == null) {
            return "redirect:/login";
        }

        blogServices.deleteBlog(blogId);

        return "redirect:/blog-manage";
    }
}