package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Database.Database;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;

/** The service for the load method
 *
 */

public class loadService {

    /** Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database
     *
     * @param r the LoadRequest Object
     * @return the LoadResult Object
     */

    public LoadResult load(LoadRequest r){

        LoadResult result = new LoadResult("");

        int numUsers = 0;
        int numPersons = 0;
        int numEvents = 0;

        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            db.clearTables();
            UserDAO uDao = new UserDAO(conn);
            PersonDAO pDao = new PersonDAO(conn);
            EventDAO eDao = new EventDAO(conn);
            for(int i = 0; i < r.getUsers().size(); i++){
                uDao.createUser(r.getUsers().get(i));
                numUsers++;
            }
            for(int j = 0; j < r.getPersons().size(); j++){
                pDao.createPerson(r.getPersons().get(j));
                numPersons++;
            }
            for(int k = 0; k < r.getEvents().size(); k++){
                eDao.createEvent(r.getEvents().get(k));
                numEvents++;
            }
            result.setMessage("Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database.");
            db.closeConnection(true);
        } catch(DataAccessException e){
            result.setMessage("DataAccess Error");
            e.printStackTrace();
        }

        return result;
    }
}
