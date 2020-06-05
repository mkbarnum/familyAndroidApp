package DataAccess;

import Model.Event;
import Model.Person;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Operations for Events
 *
 * @author Matthew Barnum
 * */
public class EventDAO {
    private Connection conn;

    /** Creates new instance of EventDAO, takes in no arguments
     * */

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /** Gets a specific event from the database
     *
     * @param eventID The event ID that is attached to the event
     * @return an event object
     * */

    public Event getEvent(String eventID) throws DataAccessException {
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


    /** Creates an event in the database
     *
     * @param event the event that will be created
     * */

    public boolean createEvent(Event event) throws DataAccessException{

        boolean commit = true;

        String sql = "INSERT INTO Events (eventID,associatedUsername,personID,latitude,longitude,country,city,eventType,year) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,event.getEventID());
            stmt.setString(2,event.getAssociatedUsername());
            stmt.setString(3,event.getPersonID());
            stmt.setFloat(4,event.getLatitude());
            stmt.setFloat(5,event.getLongitude());
            stmt.setString(6,event.getCountry());
            stmt.setString(7,event.getCity());
            stmt.setString(8,event.getEventType());
            stmt.setInt(9,event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered when creating a new event");
        }
        return true;
    }


    /** Deletes all events in the database
     *
     * */
    public void deleteAll() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear tables");
        }
    };

    public void deleteByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear rows by username");
        }
    }

    /** Gets all events from the current database associated with a user
     *
     * @return a list of events
     * */


    public List<Event> getByUsername(String username) throws DataAccessException{
        List<Event> events = new ArrayList<Event>();
        Event event;
        ResultSet rs = null;

        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
                while (rs.next()) {
                    event = new Event(rs.getString("eventID"),rs.getString("associatedUsername"),rs.getString("personID"),
                            rs.getFloat("latitude"),rs.getFloat("longitude"),rs.getString("country"),rs.getString("city"),
                            rs.getString("eventType"),rs.getInt("year"));
                    events.add(event);
                }
                return events;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }

            }
        }
    }

}
