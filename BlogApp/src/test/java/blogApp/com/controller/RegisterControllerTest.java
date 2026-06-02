package blogApp.com.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blogApp.com.services.AccountServices;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
	@MockitoBean
	private AccountServices accountServices;
	
	@Autowired
	private MockMvc mockMvc;
	@BeforeEach
	public void prepareData() {
	    when(accountServices.createAccount(any(), any(), any())).thenReturn(false);

	    when(accountServices.createAccount("alice@test.com", "Akemi", "1234abcd"))
	        .thenReturn(true);
	}
	  @Test
	    public void testGetRegisterPage_Succeed() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/register");

	        mockMvc.perform(request)
	                .andExpect(view().name("register.html"));
	}
	@Test
	public void testRegister_CorrectInfo_Succeed() throws Exception {
	    RequestBuilder request = MockMvcRequestBuilders
	            .post("/register/process")
	            .param("accountEmail", "alice@test.com")
	            .param("accountName", "Akemi")
	            .param("password", "1234abcd");

	    mockMvc.perform(request)
	            .andExpect(view().name("login.html"));
	}
	 @Test
	    public void testRegister_DuplicateEmail_Fail() throws Exception {
	        RequestBuilder request = MockMvcRequestBuilders
	                .post("/register/process")
	                .param("accountName", "Akemi")
	                .param("accountEmail", "alice@test.com")
	                .param("password", "123456");

	        mockMvc.perform(request)
	                .andExpect(view().name("register.html"));
	    }
}
