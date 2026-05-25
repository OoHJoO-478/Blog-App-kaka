package blogApp.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogApp.com.models.dao.AccountDao;
import blogApp.com.models.entity.Account;

//アカウントに関する処理を行うサービスクラス
//会員登録やログインチェックなどの業務ロジックを担当する
@Service
public class AccountServices {
	// accountテーブルを操作するDAO
	@Autowired
	private AccountDao accountDao;
	// 新規アカウントを作成する処理
	public boolean createAccount(String accountEmail,String accountName,String password) {
		// 入力されたメールアドレスがすでに登録されているか確認する
		if(accountDao.findByAccountEmail(accountEmail)==null) {
			
			// メールアドレスが未登録の場合、新しいアカウントを作成して保存する
			accountDao.save(new Account(accountEmail,accountName,password));
			// 登録成功を表すため true を返す
			return true;
		}else {
			// すでに同じメールアドレスが存在する場合、登録しない
            // 登録失敗を表すため false を返す
			return false;
		}
	}
	// ログイン時に、メールアドレスとパスワードが一致するアカウントを確認する処理
	public Account loginCheck(String accountEmail,String password) {
		// メールアドレスとパスワードに一致するアカウントを検索する
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		// 該当するアカウントが存在しない場合は null を返す
		if(account == null) {
			return null;
		}else {
			// 該当するアカウントが存在する場合は、そのアカウント情報を返す
			return account;
		}
	}
}
