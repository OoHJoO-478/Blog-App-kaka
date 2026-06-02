package blogApp.com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import blogApp.com.models.dao.AccountDao;
import blogApp.com.models.entity.Account;
import blogApp.com.services.AccountServices;

@SpringBootTest
public class AccountServiceTest {
	@MockitoBean
	private AccountDao accountDao;
	
	@Autowired
	private AccountServices accountServices;
	
	   @BeforeEach
	    public void prepareData() {
	        Account alice = new Account("alice@test.com", "Akemi", "1234abcd");

	        // 默认：邮箱不存在
	        when(accountDao.findByAccountEmail(any())).thenReturn(null);

	        // alice@test.com 已经存在
	        when(accountDao.findByAccountEmail("alice@test.com")).thenReturn(alice);

	        // 默认：登录失败
	        when(accountDao.findByAccountEmailAndPassword(any(), any())).thenReturn(null);

	        // 正确邮箱+密码时，登录成功
	        when(accountDao.findByAccountEmailAndPassword("alice@test.com", "1234abcd"))
	                .thenReturn(alice);
	    }
	   // 新規メールアドレスで会員登録成功テスト
	   @Test
	    public void testCreateAccount_NewEmail_ReturnTrue() {
	        boolean result = accountServices.createAccount("bob@test.com", "Bob", "bob12345");

	        assertTrue(result);
	    }
	   // 既存メールアドレスで会員登録失敗テスト
	    @Test
	    public void testCreateAccount_ExistingEmail_ReturnFalse() {
	        boolean result = accountServices.createAccount("alice@test.com", "Akemi", "1234abcd");

	        assertFalse(result);
	    }
	    // 正しいログイン情報でログイン成功テスト
	    @Test
	    public void testLoginCheck_CorrectInfo_ReturnAccount() {
	        Account account = accountServices.loginCheck("alice@test.com", "1234abcd");

	        assertNotNull(account);
	        assertEquals("alice@test.com", account.getAccountEmail());
	        assertEquals("Akemi", account.getAccountName());
	        assertEquals("1234abcd", account.getPassword());
	    }
	    // 誤ったログイン情報でログイン失敗テスト
	    @Test
	    public void testLoginCheck_WrongInfo_ReturnNull() {
	        Account account = accountServices.loginCheck("wrong@test.com", "1234");

	        assertNull(account);
	    }

}
