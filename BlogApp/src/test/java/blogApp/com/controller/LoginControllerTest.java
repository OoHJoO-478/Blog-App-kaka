package blogApp.com.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;

import blogApp.com.models.entity.Account;
import blogApp.com.services.AccountServices;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	@MockitoBean
	private AccountServices accountServices;
	
	@Autowired
	private MockMvc mockMvc;
	@BeforeEach
	public void prepareData() {
	    Account loginAccount = new Account("alice@test.com", "Akemi", "1234abcd");

	    when(accountServices.loginCheck(any(), any())).thenReturn(null);
	    when(accountServices.loginCheck("alice@test.com", "1234abcd")).thenReturn(loginAccount);
	}
	@Test
	public void testGetLoginPage_Succeed() throws Exception{
		RequestBuilder request  = MockMvcRequestBuilders
				.get("/login");
		mockMvc.perform(request)
		.andExpect(view().name("login.html"));
	}
	@Test
	public void testLogin_CorrectInfo_Succeed() throws Exception{
	    RequestBuilder request = MockMvcRequestBuilders
	            .post("/login/process")
	            .param("accountEmail", "alice@test.com")
	            .param("password", "1234abcd");

	    mockMvc.perform(request)
	            .andExpect(redirectedUrl("/blog-list"));
	}
	@Test
	public void testLogin_CorrectInfo_Fail() throws Exception{
	    RequestBuilder request = MockMvcRequestBuilders
	            .post("/login/process")
	            .param("accountEmail", "wrong@test.com")
	            .param("password", "1234");

	    mockMvc.perform(request)
	            .andExpect(view().name("login.html"));
	}	

}
