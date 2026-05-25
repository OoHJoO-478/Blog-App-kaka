package blogApp.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blogApp.com.models.entity.Blog;

//Blogテーブルに対してデータベース操作を行うDAO
@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {

	// ブログ記事を保存・更新する
    // 新規投稿時と編集時に使用する
    Blog save(Blog blog);
    // すべてのブログ記事を取得する
    List<Blog> findAll();

    // blogIdを指定して、1件のブログ記事を取得する
    // 該当する記事が存在しない可能性があるため、Optionalで受け取る
    Optional<Blog> findById(Long blogId);

    // blogIdを指定して、ブログ記事を削除する
    void deleteById(Long blogId);

    // accountIdを指定して、そのユーザーが投稿した記事一覧を取得する
    List<Blog> findByAccountId(Long accountId);

    // カテゴリ名を指定して、該当するブログ記事一覧を取得する
    List<Blog> findByCategoryName(String categoryName);
    // ブログタイトルにキーワードを含む記事を検索する
    List<Blog> findByBlogTitleContaining(String keyword);

}
