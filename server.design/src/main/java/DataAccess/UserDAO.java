package DataAccess;

import Model.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Operations for Users
 *
 * @author Matthew Barnum
 * */
public class UserDAO {
    private Connection conn;

    /** Creates new instance of UserDAO, takes in no arguments
     * */

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /** Creates a new user in the database
     *
     * @param user the user that will be created
     * */

    public boolean createUser(User user) throws DataAccessException {
        boolean commit = true;

        String sql = "INSERT INTO Users (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getFirstName());
            stmt.setString(5,user.getLastName());
            stmt.setString(6,user.getGender());
            stmt.setString(7,user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered when creating a new user");
        }
        return commit;
    }

    /** Gets a specific from the database
     *
     * @param username the username of the User
     * @return a user object
     * */

    public User getUser(String username) throws DataAccessException{

        User user = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next() == true){
                user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"),rs.getString("firstName"),rs.getString("lastName"),
                rs.getString("gender"),rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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

    /** Deletes all users in the database
     *
     * */

    public void deleteAll() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear tables");
        }
    }




}
