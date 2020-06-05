package DataAccess;

import Model.Person;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Operations for Persons
 *
 * @author Matthew Barnum
 * */
public class PersonDAO {

    private Connection conn;



    /** Creates new instance of PersonDAO, takes in no arguments
     * */

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createPerson(Person person) throws DataAccessException {
        boolean commit = true;

        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,person.getPersonID());
            stmt.setString(2,person.getAssociatedUsername());
            stmt.setString(3,person.getFirstName());
            stmt.setString(4,person.getLastName());
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFatherID());
            stmt.setString(7,person.getMotherID());
            stmt.setString(8,person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered when creating a new person");
        }
        return true;
    }


    public Person getPerson(String personID) throws DataAccessException{

        Person person = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next() == true){
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"),rs.getString("lastName"),rs.getString("gender"),
                        rs.getString("fatherID"),rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
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

        return null;
    }



    /** Deletes all persons in the database
     *
     * */

    public void deleteAll() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear tables");
        }
    }

    public void deleteByUsername(String username) throws DataAccessException{
        String sql = "DELETE FROM Persons WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear rows by username");
        }

    }

    public List<Person> getByUsername(String username) throws DataAccessException{
        List<Person> persons = new ArrayList<Person>();
        Person person;
        ResultSet rs = null;

        String sql = "SELECT * FROM Persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
                while (rs.next()) {
                    person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                            rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                            rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                    persons.add(person);
                }
                return persons;
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
