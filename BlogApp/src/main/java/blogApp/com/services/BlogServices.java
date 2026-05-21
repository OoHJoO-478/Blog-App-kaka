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

    public void createBlog(String blogTitle,
                           String categoryName,
                           String blogImage,
                           String article,
                           Long accountId) {

        Blog blog = new Blog(blogTitle, categoryName, blogImage, article, accountId);
        blogDao.save(blog);
    }
    public Blog findBlogById(Long blogId) {
        return blogDao.findById(blogId).orElse(null);
    }

    public void updateBlog(Long blogId,
                           String blogTitle,
                           String categoryName,
                           String blogImage,
                           String article,
                           Long accountId) {

        Blog blog = blogDao.findById(blogId).orElse(null);

        if (blog != null) {
            blog.setBlogTitle(blogTitle);
            blog.setCategoryName(categoryName);
            blog.setBlogImage(blogImage);
            blog.setArticle(article);
            blog.setAccountId(accountId);

            blogDao.save(blog);
        }
    }
}