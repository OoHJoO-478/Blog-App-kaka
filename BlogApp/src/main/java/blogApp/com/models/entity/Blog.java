package blogApp.com.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Blog {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long blogId;

	 private String blogTitle;

	 private String categoryName;

	 private String blogImage;

	 private String article;

	 private Long accountId;

	 private LocalDateTime createdAt;

	 private LocalDateTime updatedAt;

	 public Blog() {
	 }

	 public Blog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		this.blogTitle = blogTitle;
		this.categoryName = categoryName;
		this.blogImage = blogImage;
		this.article = article;
		this.accountId = accountId;
	
	 }

	 public Long getBlogId() {
		 return blogId;
	 }

	 public void setBlogId(Long blogId) {
		 this.blogId = blogId;
	 }

	 public String getBlogTitle() {
		 return blogTitle;
	 }

	 public void setBlogTitle(String blogTitle) {
		 this.blogTitle = blogTitle;
	 }

	 public String getCategoryName() {
		 return categoryName;
	 }

	 public void setCategoryName(String categoryName) {
		 this.categoryName = categoryName;
	 }

	 public String getBlogImage() {
		 return blogImage;
	 }

	 public void setBlogImage(String blogImage) {
		 this.blogImage = blogImage;
	 }

	 public String getArticle() {
		 return article;
	 }

	 public void setArticle(String article) {
		 this.article = article;
	 }

	 public Long getAccountId() {
		 return accountId;
	 }

	 public void setAccountId(Long accountId) {
		 this.accountId = accountId;
	 }

	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 public LocalDateTime getUpdatedAt() {
		 return updatedAt;
	 }

	 public void setUpdatedAt(LocalDateTime updatedAt) {
		 this.updatedAt = updatedAt;
	 }
	 
	 
}


