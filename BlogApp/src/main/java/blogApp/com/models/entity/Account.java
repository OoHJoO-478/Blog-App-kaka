package blogApp.com.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//accountテーブルと対応するEntityクラス
//データベースのアカウント情報をJavaのオブジェクトとして扱うためのクラス
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	private String accountEmail;
	
	private String accountName;
	
	private String password;
	
	// 登録日
    // データベース側で自動生成するため、JavaからINSERTしない
	@Column(insertable = false)
	private LocalDateTime registerDate;

	// デフォルトコンストラクタ
    // JPAがEntityを生成するために必要
	public Account() {
	}
	// 会員登録時に使用するコンストラクタ
    // 登録日はデータベース側で自動生成されるため、ここでは設定しない
	public Account(String accountEmail, String accountName, String password) {
	    this.accountEmail = accountEmail;
	    this.accountName = accountName;
	    this.password = password;
	}

	

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	
	

}
