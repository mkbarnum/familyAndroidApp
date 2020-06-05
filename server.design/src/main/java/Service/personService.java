package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Database.Database;
import Model.Event;
import Model.Person;
import Request.PersonRequest;
import Result.EventResult;
import Result.PersonResult;

import java.sql.Connection;
import java.util.List;

/** The service for the person methods
 *
 */

public class personService {

    /** Returns the person objects being looked for
     *
     * @param r the PersonRequest object
     * @return the PersonResult object
     */

    public static PersonResult findPerson(PersonRequest r){

        PersonResult result = new PersonResult("");
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            PersonDAO personDAO = new PersonDAO(conn);


            if (r.getPersonID() == null) {
                String username = r.getUsername();
                List<Person> persons = personDAO.getByUsername(username);
                result.setData(persons);
                result.setMessage(null);
            } else {
                String personID = r.getPersonID();
                Person person = personDAO.getPerson(personID);
                if(person == null){
                    result.setMessage("PersonID not found in Database");
                }
                else if(!person.getAssociatedUsername().equals(r.getUsername())){
                    result.setMessage("Person does not belong to this user");
                }
                else {
                    result.setFoundPerson(person);
                    result.setMessage(null);
                }
            }
            db.closeConnection(true);
        } catch(DataAccessException e){
            result.setMessage("DataAccess error!");
            e.printStackTrace();
        }

        return result;
    }
}
