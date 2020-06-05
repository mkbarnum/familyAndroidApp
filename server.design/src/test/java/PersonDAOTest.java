import DataAccess.DataAccessException;
import DataAccess.PersonDAO;
import Database.Database;
import Model.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class PersonDAOTest {
    Database db;
    Person bestPerson;

    @Before
    public void setUp() throws Exception {
        db = new Database();

        bestPerson = new Person("abc123" , "mkbarnum" ,"matt","barnum","m","abc","abcd","abcde");
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
        Person comparePerson = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            comparePerson = pDao.getPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(comparePerson);
        assertEquals(bestPerson, comparePerson);
    }

    @Test
    public void createNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            pDao.createPerson(bestPerson);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);

        Person comparePerson = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            comparePerson = pDao.getPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNull(comparePerson);
    }

    @Test
    public void getPersonPositive() throws Exception {
        Person comparePerson = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            comparePerson = pDao.getPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(comparePerson);

        boolean isEqual = comparePerson.equals(bestPerson);

        assertTrue(isEqual);
    }

    @Test
    public void getPersonNegative() throws Exception{
        Person comparePerson = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            comparePerson = pDao.getPerson("fakeID");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        assertNotEquals(comparePerson, bestPerson);
    }

    @Test
    public void deletePositive() throws Exception{
        Person comparePerson = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            comparePerson = pDao.getPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertEquals(comparePerson, bestPerson);

        try {
            Connection conn = db.openConnection();
            db.clearTables();
            PersonDAO pDao = new PersonDAO(conn);
            pDao.createPerson(bestPerson);
            pDao.deleteAll();
            comparePerson = pDao.getPerson(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotEquals(comparePerson,bestPerson);
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
            PersonDAO pDao = new PersonDAO(conn);
            pDao.deleteAll();
            db.closeConnection(true);
        } catch (DataAccessException e){
            didItWork = false;
            db.closeConnection(false);
        }

        assertFalse(didItWork);
    }
}
