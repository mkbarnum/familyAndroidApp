package Service;

import DataAccess.DataAccessException;
import Database.Database;
import Result.ClearResult;

/** Service for clear method
 *
 */

public class clearService {

    /** Deletes ALL data from the database, including user accounts, auth tokens, and
     * generated person and event data.
     *
     */
    public ClearResult clear(){

        boolean cleared;

        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            cleared = true;
        } catch (DataAccessException e){
            cleared = false;
            System.out.println("Couldn't clear tables");
        }

        ClearResult result = new ClearResult("");

        if(cleared){
            result.setMessage("Clear Succeeded");
        }
        else {
            result.setMessage("Clear failed");
        }

        return result;
    }
}
