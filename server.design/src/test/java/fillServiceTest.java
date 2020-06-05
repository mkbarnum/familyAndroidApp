import Database.Database;
import Request.FillRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Service.fillService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class fillServiceTest {
    Database db;
    FillRequest fillRequest = new FillRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();
        fillRequest.setHasGeneration(true);
        fillRequest.setGenerations(4);
        fillRequest.setUsername("mattyb");
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
    public void fillPositive() {
        FillResult result;
        fillService fService = new fillService();
        result = fService.fill(fillRequest);
        String compareMessage = "Successfully added 31 persons and 93 events to the database.";
        assertNotNull(result);
        assertEquals(compareMessage,result.getMessage());
    }

    @Test
    public void fillNegative() {
        FillResult result;
        fillService fService = new fillService();
        fillRequest.setUsername("matt");
        result = fService.fill(fillRequest);
        String compareMessage = "Username not found";
        assertNotNull(result);
        assertEquals(compareMessage,result.getMessage());
    }
}
