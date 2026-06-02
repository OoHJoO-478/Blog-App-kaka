package blogApp.com.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RegisterAndLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // 新規ユーザー登録後、同じメールアドレスとパスワードでログインできることを確認する
    @Test
    public void testRegisterAndLogin_NewAccount_Succeed() throws Exception {

        // 登録画面を表示する
        RequestBuilder request = MockMvcRequestBuilders
                .get("/register");

        mockMvc.perform(request)
                .andExpect(view().name("register.html"));

        // 新規ユーザーを登録する
        request = MockMvcRequestBuilders
                .post("/register/process")
                .param("accountName", "Akemi")
                .param("accountEmail", "akemi@test.com")
                .param("password", "1234abcd");

        mockMvc.perform(request)
                .andExpect(view().name("login.html"));

        // 登録したユーザー情報でログインする
        request = MockMvcRequestBuilders
                .post("/login/process")
                .param("accountEmail", "akemi@test.com")
                .param("password", "1234abcd");

        mockMvc.perform(request)
                .andExpect(redirectedUrl("/blog-list"))
                .andExpect(request().sessionAttribute("loginAccountInfo",
                        org.hamcrest.Matchers.notNullValue()));
    }

    // 未登録ユーザーでログインした場合、ログイン画面に戻ることを確認する
    @Test
    public void testLogin_UnregisteredAccount_Fail() throws Exception {

        // ログイン画面を表示する
        RequestBuilder request = MockMvcRequestBuilders
                .get("/login");

        mockMvc.perform(request)
                .andExpect(view().name("login.html"));

        // 未登録のメールアドレスとパスワードでログインする
        request = MockMvcRequestBuilders
                .post("/login/process")
                .param("accountEmail", "notfound@test.com")
                .param("password", "1234abcd");

        mockMvc.perform(request)
                .andExpect(view().name("login.html"));
    }

    // 同じメールアドレスで再登録できないことを確認する
    @Test
    public void testRegister_DuplicateEmail_Fail() throws Exception {

        // 1回目の登録
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register/process")
                .param("accountName", "Akemi")
                .param("accountEmail", "akemi@test.com")
                .param("password", "1234abcd");

        mockMvc.perform(request)
                .andExpect(view().name("login.html"));

        // 同じメールアドレスで2回目の登録を行う
        request = MockMvcRequestBuilders
                .post("/register/process")
                .param("accountName", "Akemi")
                .param("accountEmail", "akemi@test.com")
                .param("password", "1234abcd");

        mockMvc.perform(request)
                .andExpect(view().name("register.html"));
    }
}