import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Database.Database;
import Model.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class EventDAOTest {
    Database db;
    Event bestEvent;

    @Before
    public void setUp() throws Exception {
        db = new Database();

        bestEvent = new Event("abc123" , "mkbarnum" ,"matt",-23f,32.8f,"abc","abcd","abcde",2012);
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
        Event compareEvent = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            compareEvent = eventDAO.getEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareEvent);
        assertEquals(bestEvent, compareEvent);
    }

    @Test
    public void createNegative() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            eventDAO.createEvent(bestEvent);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);

        Event compareEvent = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            compareEvent = eventDAO.getEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareEvent);
    }

    @Test
    public void getPersonPositive() throws Exception {
        Event compareEvent = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            compareEvent = eventDAO.getEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotNull(compareEvent);

        boolean isEqual = compareEvent.equals(bestEvent);

        assertTrue(isEqual);
    }

    @Test
    public void getPersonNegative() throws Exception{
        Event compareEvent = null;
        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            compareEvent = eventDAO.getEvent("fakeID");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        assertNotEquals(compareEvent, bestEvent);
    }

    @Test
    public void deletePositive() throws Exception{
        Event compareEvent = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

        try {
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            compareEvent = eventDAO.getEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertEquals(compareEvent, bestEvent);

        try {
            Connection conn = db.openConnection();
            db.clearTables();
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.createEvent(bestEvent);
            eventDAO.deleteAll();
            compareEvent = eventDAO.getEvent(bestEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e){
            db.closeConnection(false);
        }

        assertNotEquals(compareEvent,bestEvent);
    }

    @Test
    public void deleteNegative() throws Exception {
        boolean didItWork = false;
        try {
            Connection conn = db.openConnection();
            try (Statement stmt = conn.createStatement()){
                String sql = "DROP TABLE Persons";
                stmt.executeUpdate(sql);
            } catch (SQLException e){
                throw new DataAccessException("Unable to delete tables");
            }
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.deleteAll();
            db.closeConnection(true);
        } catch (DataAccessException e){
            didItWork = true;
            db.closeConnection(false);
        }

        assertFalse(didItWork);
    }
}
