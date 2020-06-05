import Database.Database;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Result.LoginResult;
import Service.fillService;
import Service.loginService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class loginServiceTest {
    Database db;
    LoginRequest loginRequest = new LoginRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();
        loginRequest.setPassword("abc");
        loginRequest.setUserName("mattyb");
        RegisterRequest registerRequest = new RegisterRequest("mattyb","abc","matt@gmail.com","matt","barnum",'m');
        registerService registerService = new registerService();
        registerService.register(registerRequest);
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    @After
    public void destroy() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loginPositive() {
        LoginResult result;
        loginService lService = new loginService();
        result = lService.login(loginRequest);
        String user = "mattyb";
        assertNotNull(result);
        assertEquals(user,result.getUserName());
    }

    @Test
    public void loginNegative() {
        LoginResult result;
        loginService lService = new loginService();
        loginRequest.setUserName("fake");
        result = lService.login(loginRequest);
        String badResponse = "Username not found";
        assertNotNull(result);
        assertEquals(badResponse,result.getMessage());

        loginRequest.setUserName("mattyb");
        loginRequest.setPassword("bad");
        result = lService.login(loginRequest);
        badResponse = "Password did not match";
        assertNotNull(result);
        assertEquals(badResponse,result.getMessage());

    }
}
