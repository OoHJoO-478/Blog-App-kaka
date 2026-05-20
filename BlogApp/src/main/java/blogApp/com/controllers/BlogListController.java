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

@Controller
public class BlogListController {
	@Autowired
	private HttpSession session;
	@Autowired
	private BlogServices blogServices;
	
	@GetMapping("/blog-list")
	public String getBlogList(Model model) {
		Account account = (Account)session.getAttribute("loginAccountInfo");
		
		if(account == null) {
			return "redirect:/login";
			
		}else {
			List<Blog>BlogList = blogServices.selectAllBlogList(account.getAccountId());
			model.addAttribute("accountName",account.getAccountName());
			model.addAttribute("blogList",BlogList);
			return "blog-list";
		}
	}
}
