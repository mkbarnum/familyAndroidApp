package Service;

import DataAccess.*;
import Database.Database;
import FamilyData.familyData;
import FamilyTree.FamilyTree;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.sql.Connection;

/** The Service for the fill method
 *
 */
public class fillService {

    /** Populates the server's database with generated data for the specified user name.
     *
     * @param r the FillRequest object
     * @return the FillResult object
     */
    public FillResult fill(FillRequest r){
        FillResult result = new FillResult();
        int numGens;
        int numPersons;
        String username = r.getUsername();

        if(r.isHasGeneration()){
            numGens = r.getGenerations();
        }
        else{
            numGens = 4;
        }

        numPersons = (int)countPeople(numGens+1);
        int numEvents = numPersons*3;

        if(hasUsername(username)){
            clearUserData(username);

            familyData familyData = new familyData();
            familyData.createData();

            FamilyTree familyTree = new FamilyTree();
            familyTree.setMainUser(getUser(username));
            familyTree.setFamilyData(familyData);
            familyTree.generateTree(numGens);

            result.setMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database.");
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

    private double countPeople(int numGens){
        double result = 0;
        numGens--;
        if(numGens>0) {
            double gens = new Double(numGens);
            double d = 2;
            result = Math.pow(d,numGens);
            result += countPeople(numGens);
        }
        if(numGens==0){
            return 1;
        }

        return result;
    }

    private void clearUserData(String username){
        try{
            Database db = new Database();
            Connection conn = db.openConnection();
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            AuthDAO authDAO = new AuthDAO(conn);

            authDAO.deleteByUsername(username);
            personDAO.deleteByUsername(username);
            eventDAO.deleteByUsername(username);
            db.closeConnection(true);
        } catch(DataAccessException e){
            e.printStackTrace();
        }
    }

    private User getUser(String username){
        User mainUser = new User();
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            UserDAO uDao = new UserDAO(conn);
            mainUser = uDao.getUser(username);
            db.closeConnection(true);
        } catch( DataAccessException e ) {
            e.printStackTrace();
        }
        return mainUser;
    }

}
