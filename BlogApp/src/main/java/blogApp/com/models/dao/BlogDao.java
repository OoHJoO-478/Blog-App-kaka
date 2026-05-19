package blogApp.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blogApp.com.models.entity.Blog;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {

    Blog save(Blog blog);

    List<Blog> findAll();

    Optional<Blog> findById(Long blogId);

    void deleteById(Long blogId);

    List<Blog> findByAccountId(Long accountId);

    List<Blog> findByCategoryName(String categoryName);

    List<Blog> findByBlogTitleContaining(String keyword);

}
