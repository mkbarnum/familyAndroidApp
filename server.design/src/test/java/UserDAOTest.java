import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Database.Database;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class UserDAOTest {
    Database db;
    User bestUser;

    @Before
    public void setUp() throws Exception {
        db = new Database();

        bestUser = new User("mkbarnum", "abc", "mattyb@gmail.com", "matthew", "barnum", "m", "123456");
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
    public void createPositive() throws Exception {
        User compareUser = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            compareUser = uDao.getUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareUser);
        assertEquals(bestUser, compareUser);
    }

    @Test
    public void createNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            uDao.createUser(bestUser);
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);

        User compareUser = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            compareUser = uDao.getUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNull(compareUser);
    }

    @Test
    public void getUserPositive() throws Exception {
        User compareUser = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            compareUser = uDao.getUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareUser);

        boolean isEqual = compareUser.equals(bestUser);

        assertTrue(isEqual);
    }

    @Test
    public void getUserNegative() throws Exception{
        boolean didItWork = true;
        User compareUser = null;
        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            compareUser = uDao.getUser("fakeUsername");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertNotEquals(compareUser, bestUser);
    }

    @Test
    public void deletePositive() throws Exception{
        User compareUser = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            compareUser = uDao.getUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertEquals(compareUser, bestUser);

        try {
            Connection conn = db.openConnection();
            db.clearTables();
            UserDAO uDao = new UserDAO(conn);
            uDao.createUser(bestUser);
            uDao.deleteAll();
            compareUser = uDao.getUser(bestUser.getUsername());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotEquals(compareUser,bestUser);
    }

    @Test
    public void deleteNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            try (Statement stmt = conn.createStatement()){
                String sql = "DROP TABLE Users";
                stmt.executeUpdate(sql);
            } catch (SQLException e){
                throw new DataAccessException("Unable to delete tables");
            }
            UserDAO uDao = new UserDAO(conn);
            uDao.deleteAll();
            db.closeConnection(true);
        } catch (DataAccessException e){
            didItWork = false;
            db.closeConnection(false);
        }

        assertFalse(didItWork);
    }

}
