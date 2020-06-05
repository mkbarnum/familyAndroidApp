package Result;

/** RegisterResult object
 *
 */

public class RegisterResult {
    /** authToken of user registered
     *
     */
    private String authToken;
    /** username of new user
     *
     */
    private String userName;
    /** personID of new user
     *
     */
    private String personID;
    /** message returned to the handler
     *
     */
    private String message;
    /** Constructor that sets values
     *
     */

    public RegisterResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
    /** Constructor that sets message value
     *
     */

    public RegisterResult(String message){
        this.message = message;
    }

    public RegisterResult(){}

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

    @Override
    public String toString() {
        return "RegisterResult{" +
                "authToken='" + authToken + '\'' +
                ", userName='" + userName + '\'' +
                ", personID='" + personID + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
