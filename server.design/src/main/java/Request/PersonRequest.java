package Request;

/** The PersonRequest Object
 *
 */

public class PersonRequest {
    /** The personID attached to the person that will be found
     *
     */
    String personID;
    String username;

    /** Constructor of the PersonRequest object
     *
     */

    public PersonRequest(String personID) {
        this.personID = personID;
    }
    public PersonRequest(){}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
