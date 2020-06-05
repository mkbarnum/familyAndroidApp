package Service;

import DataAccess.AuthDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Database.Database;
import FamilyData.familyData;
import FamilyTree.FamilyTree;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.sql.Connection;

/** the service for a register method
 *
 */
public class registerService {

    /** Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth token
     *
     * @param r the RegisterRequest Object
     * @return the RegisterResult Object
     */

    public RegisterResult register(RegisterRequest r){

        RegisterResult result = new RegisterResult();

        Database db = new Database();


            if (!hasUser(r)) {
                try {
                    Connection conn = db.openConnection();
                    UserDAO userDAO = new UserDAO(conn);
                    AuthDAO authDAO = new AuthDAO(conn);
                    String gender = "" + r.getGender();
                    User newUser = new User(r.getUserName(),r.getPassword(),r.getEmail(),r.getFirstName(),r.getLastName(),gender, Person.randomPersonID());
                    userDAO.createUser(newUser);
                    AuthToken token = new AuthToken(AuthToken.generateAuthToken(),newUser.getUsername());
                    authDAO.createAuth(token);
                    db.closeConnection(true);
                    familyData familyData = new familyData();
                    familyData.createData();

                    FamilyTree familyTree = new FamilyTree();
                    familyTree.setMainUser(newUser);
                    familyTree.setFamilyData(familyData);
                    familyTree.generateTree(4);

                    result.setAuthToken(token.getAuthToken());
                    result.setPersonID(newUser.getPersonID());
                    result.setUserName(newUser.getUsername());


                } catch(DataAccessException e){
                    e.printStackTrace();
                    result.setMessage("Error accessing Database");
                }
            } else {
                result.setMessage("Username already taken.");
            }



        return result;
    }

    private boolean hasUser(RegisterRequest r){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            UserDAO userDAO = new UserDAO(conn);
            if(userDAO.getUser(r.getUserName())==null){
                db.closeConnection(true);
                return false;
            }
            else {
                db.closeConnection(true);
                return true;
            }
        }
        catch (DataAccessException e){
            e.printStackTrace();
            return true;
        }

    }
}
