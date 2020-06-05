package DataAccess;

import Model.AuthToken;
import Model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Operations for AuthTokens
 *
 * @author Matthew Barnum
 * */

public class AuthDAO {
    private Connection conn;
    /** Creates new instance of AuthDao
     * */

    public AuthDAO(Connection conn) {
        this.conn = conn;
    }

    /** Gets a specific authToken from the database
     *
     * @return an authToken Object
     * @param auth the auth attached to the authToken
     * */

    public AuthToken getAuth(String auth) throws DataAccessException{

        AuthToken token;
        ResultSet rs = null;

        String sql = "SELECT * FROM AuthTokens WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, auth);
            rs = stmt.executeQuery();
            if(rs.next() == true){
                token = new AuthToken(rs.getString("authToken"),rs.getString("associatedUsername"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding auth");
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

    /** Creates an authToken in the database
     *
     * @param token     The authToken that will be created in the database
     * */

    public boolean createAuth(AuthToken token) throws DataAccessException{
        boolean commit = true;
        String sql = "INSERT INTO AuthTokens (authToken, associatedUsername) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,token.getAuthToken());
            stmt.setString(2,token.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered when creating a new auth");
        }
        return true;
    }

    public void deleteByUsername(String username) throws DataAccessException{
        String sql = "DELETE FROM AuthTokens WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Unable to clear rows by username");
        }
    }

    /** Deletes all authTokens from the database
     *
     * */

    public void deleteAll() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthTokens";
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear tables");
        }
    }

}
