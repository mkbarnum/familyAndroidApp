package Result;

/** The LoginResult Object
 *
 */
public class LoginResult {
    /** Authtoken of user that logged in
     *
     */
    private String authToken;
    /** username of user
     *
     */
    private String userName;
    /** personid of user
     *
     */
    private String personID;
    /** message that will be returned to handler
     *
     */
    private String message;

    /** Constructor for LoginResult, sets values
     *
     */

    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
    /** Constructor for LoginResults, sets message value
     *
     */

    public LoginResult(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

