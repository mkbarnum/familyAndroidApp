import DataAccess.AuthDAO;
import DataAccess.DataAccessException;
import Database.Database;
import Model.AuthToken;
import Result.ClearResult;
import Service.clearService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class clearServiceTest {
    Database db;
    Service.clearService clearService = new clearService();

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
    public void clearPositive() {
        ClearResult result;
        result = clearService.clear();
        ClearResult compareResult = new ClearResult("Clear Succeeded");
        assertNotNull(result);
        assertEquals(compareResult.getMessage(),result.getMessage());
    }

    @Test
    public void clearNegative() {
        ClearResult result;
        result = clearService.clear();
        ClearResult compareResult = new ClearResult("ClearSucceeded");
        assertNotNull(result);
        assertNotEquals(compareResult.getMessage(),result.getMessage());
    }

}
