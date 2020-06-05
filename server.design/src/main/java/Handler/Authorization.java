package Handler;

import DataAccess.AuthDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Database.Database;
import Model.AuthToken;
import Model.User;

import java.sql.Connection;

public class Authorization {
    public static boolean tokenExists(String authToken){
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            AuthDAO authDAO = new AuthDAO(conn);

            AuthToken token = authDAO.getAuth(authToken);
            db.closeConnection(true);

            if(token != null){
                return true;
            }
            else{
                return false;
            }

        } catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    public static AuthToken getToken(String authToken){
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            AuthDAO aDao = new AuthDAO(conn);

            AuthToken token = aDao.getAuth(authToken);
            db.closeConnection(true);

            return token;

        } catch(DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }
}
