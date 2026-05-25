package blogApp.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogApp.com.models.dao.BlogDao;
import blogApp.com.models.entity.Blog;

//ブログ記事に関する処理を行うサービスクラス
//記事一覧取得、投稿、編集、削除などの業務ロジックを担当する
@Service
public class BlogServices {
	// blogテーブルを操作するDAO
    @Autowired
    private BlogDao blogDao;

    // ログイン中のユーザーが投稿したブログ記事一覧を取得する処理
    public List<Blog> selectAllBlogList(Long accountId) {
    	// accountId が null の場合、記事一覧を取得できないため null を返す
        if (accountId == null) {
            return null;
        } else {
        	// accountId を使って、そのユーザーの記事一覧を取得する
            return blogDao.findByAccountId(accountId);
        }
    }
    // 新しいブログ記事を作成する処理
    public void createBlog(String blogTitle,
                           String categoryName,
                           String blogImage,
                           String article,
                           Long accountId) {
    	// フォームから受け取った内容を使って Blog オブジェクトを作成する
        Blog blog = new Blog(blogTitle, categoryName, blogImage, article, accountId);
        // 作成したブログ記事をデータベースに保存する
        blogDao.save(blog);
    }
    // blogId を使って、1件のブログ記事を取得する処理
    public Blog findBlogById(Long blogId) {
    	// 指定された blogId の記事を検索する
        // 見つからない場合は null を返す
        return blogDao.findById(blogId).orElse(null);
    }
    // ブログ記事を更新する処理
    public void updateBlog(Long blogId,
                           String blogTitle,
                           String categoryName,
                           String blogImage,
                           String article,
                           Long accountId) {
    	// 更新対象の記事を blogId で検索する
        Blog blog = blogDao.findById(blogId).orElse(null);
        // 記事が存在する場合のみ更新処理を行う
        if (blog != null) {
            blog.setBlogTitle(blogTitle);
            blog.setCategoryName(categoryName);
            blog.setBlogImage(blogImage);
            blog.setArticle(article);
            blog.setAccountId(accountId);

            blogDao.save(blog);
        }
    }
    // blogId を使って、ブログ記事を削除する処理
    public void deleteBlog(Long blogId) {
    	// 指定された blogId の記事をデータベースから削除する
        blogDao.deleteById(blogId);
    }
}