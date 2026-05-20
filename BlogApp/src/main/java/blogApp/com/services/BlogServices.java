package blogApp.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogApp.com.models.dao.BlogDao;
import blogApp.com.models.entity.Blog;

@Service
public class BlogServices {

    @Autowired
    private BlogDao blogDao;

    public List<Blog> selectAllBlogList(Long accountId) {
        if (accountId == null) {
            return null;
        } else {
            return blogDao.findByAccountId(accountId);
        }
    }
}