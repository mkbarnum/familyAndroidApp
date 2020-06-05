import Database.Database;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Service.loginService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class registerServiceTest {
    Database db;
    RegisterRequest registerRequest = new RegisterRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();

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
        RegisterResult result;
        registerRequest = new RegisterRequest("mattyb","abc","matt@gmail.com","matt","barnum",'m');
        registerService registerService = new registerService();
        result = registerService.register(registerRequest);
        assertNotNull(result.getAuthToken());
        assertNull(result.getMessage());
        assertNotNull(result.getPersonID());
        assertNotNull(result.getUserName());
    }

    @Test
    public void loginNegative() {
        RegisterResult result;
        registerRequest = new RegisterRequest("mattyb","abc","matt@gmail.com","matt","barnum",'m');
        registerService registerService = new registerService();
        result = registerService.register(registerRequest);
        result = registerService.register(registerRequest);
        String taken = "Username already taken.";
        assertNotNull(result);
        assertEquals(result.getMessage(),taken);
    }
}
