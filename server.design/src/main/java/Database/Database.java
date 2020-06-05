package Database;

import DataAccess.DataAccessException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Creates the database
 *
 */

public class Database {

    /** Creates new instance of Database, takes in no arguments
     * */
    public Database(){}

    private Connection conn;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.db";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
    } catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException{
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS Events " +
                    "(" +
                    "eventID text not null unique, " +
                    "associatedUsername text not null, " +
                    "personID text not null, " +
                    "latitude float not null, " +
                    "longitude float not null, " +
                    "country text not null, " +
                    "city text not null, " +
                    "eventType text not null, " +
                    "year int not null, " +
                    "primary key (eventID) " +
                    ")";

            stmt.executeUpdate(sql);


            sql =  "CREATE TABLE IF NOT EXISTS Users " +
                    "(" +
                    "username text not null unique, " +
                    "password text not null, " +
                    "email text not null, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "personID text not null unique, " +
                    "primary key (username) " +
                    ")";

            stmt.executeUpdate(sql);

            sql=     "CREATE TABLE IF NOT EXISTS Persons " +
                    "(" +
                    "personID text not null unique, " +
                    "associatedUsername text not null, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "fatherID text, " +
                    "motherID text, " +
                    "spouseID text, " +
                    "primary key (personID) " +
                    ")";

            stmt.executeUpdate(sql);

            sql =    "CREATE TABLE IF NOT EXISTS AuthTokens " +
                    "(" +
                    "authToken text not null unique, " +
                    "associatedUsername text not null, " +
                    "primary key (authToken) " +
                    ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL error while creating tables");
        }
    }

    public void clearTables() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){


            String sql2 = "DELETE FROM Events";
            stmt.executeUpdate(sql2);
            String sql3 = "DELETE FROM Persons";
            stmt.executeUpdate(sql3);
            String sql4 = "DELETE FROM AuthTokens";
            stmt.executeUpdate(sql4);
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            throw new DataAccessException("Unable to clear tables");
        }
    }

}
