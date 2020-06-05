package Service;

import DataAccess.AuthDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Database.Database;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;

/** The service for a login method
 *
 */

public class loginService {

    /**
     * Logs in the user and returns an auth token.
     *
     * @param r the LoginRequest object
     * @return the LoginResult object
     */

    public LoginResult login(LoginRequest r) {
        LoginResult result = new LoginResult("");

        String username = r.getUserName();
        String password = r.getPassword();
        User user = getUser(username);

        if(hasUsername(username)){
            if(correctPassword(username,password)){
                result = new LoginResult(AuthToken.generateAuthToken(),username,user.getPersonID());
                AuthToken token = new AuthToken(result.getAuthToken(),result.getUserName());
                storeAuth(token);
                return result;
            }
            else{
                result.setMessage("Password did not match");
            }
        }
        else{
            result.setMessage("Username not found");
        }


        return result;
    }

    private boolean hasUsername(String username) {

        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);

            User user = uDao.getUser(username);

            db.closeConnection(true);

            if (user != null) {
                return true;
            } else {
                return false;
            }

        } catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean correctPassword(String username, String password) {

        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);

            User user = uDao.getUser(username);
            db.closeConnection(true);

            if(user.getPassword().equals(password)){
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

    private User getUser(String username){
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);

            User user = uDao.getUser(username);
            db.closeConnection(true);

            return user;

        } catch(DataAccessException e){
            e.printStackTrace();
             return null;
        }
    }

    private void storeAuth(AuthToken token){
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            AuthDAO aDao = new AuthDAO(conn);

            aDao.createAuth(token);

            db.closeConnection(true);

        } catch(DataAccessException e){
            e.printStackTrace();
        }
    }
}