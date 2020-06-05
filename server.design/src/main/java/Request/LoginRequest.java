package Request;

/** The LoginRequest Object
 *
 */

public class LoginRequest {
    /** The Username to be logged in
     *
     */
    String userName;
    /** The password of the user
     *
     */
    String password;

    /** Constructor for LoginRequest, sets values
     *
     */

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequest(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
