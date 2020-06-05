import DataAccess.AuthDAO;
import DataAccess.DataAccessException;
import Database.Database;
import Model.AuthToken;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class AuthDAOTest {
    Database db;
    AuthToken bestToken;

    @Before
    public void setUp() throws Exception {
        db = new Database();

        bestToken = new AuthToken("12345","mattyb");
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    @After
    public void destroy() throws Exception {
        db.openConnection();
        db.closeConnection(true);
    }

    @Test
    public void createPositive() throws Exception {
        AuthToken compareToken = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            compareToken = authDAO.getAuth(bestToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareToken);
    }

    @Test
    public void createNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            authDAO.createAuth(bestToken);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);

        AuthToken compareToken = bestToken;
        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            compareToken = authDAO.getAuth(bestToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareToken);
    }

    @Test
    public void getAuthPositive() throws Exception {
        AuthToken compareToken = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            compareToken = authDAO.getAuth(bestToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareToken);

        boolean isEqual = compareToken.equals(bestToken);

        assertTrue(isEqual);
    }

    @Test
    public void getAuthNegative() throws Exception{
        AuthToken compareToken = null;
        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            compareToken = authDAO.getAuth("fakeID");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        assertNotEquals(compareToken, bestToken);
    }

    @Test
    public void deletePositive() throws Exception{
        AuthToken compareToken = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            compareToken = authDAO.getAuth(bestToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertEquals(compareToken, bestToken);

        try {
            Connection conn = db.openConnection();
            db.clearTables();
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.createAuth(bestToken);
            authDAO.deleteAll();
            compareToken = authDAO.getAuth(bestToken.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotEquals(compareToken,bestToken);
    }

    @Test
    public void deleteNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            try (Statement stmt = conn.createStatement()){
                String sql = "DROP TABLE Persons";
                stmt.executeUpdate(sql);
            } catch (SQLException e){
                throw new DataAccessException("Unable to delete tables");
            }
            AuthDAO authDAO = new AuthDAO(conn);
            authDAO.deleteAll();
            db.closeConnection(true);
        } catch (DataAccessException e){
            didItWork = false;
            db.closeConnection(false);
        }

        assertFalse(false);
    }
}
