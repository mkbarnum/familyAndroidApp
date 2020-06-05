package Request;

/** The RegisterRequest Object
 *
 */

public class RegisterRequest {
    /** userName to be registered
     *
     */
    private String userName;
    /** Password of new user
     *
     */
    private String password;
    /** email of new user
     *
     */
    private String email;
    /** firstname of new user
     *
     */
    private String firstName;
    /** last name of new user
     *
     */
    private String lastName;
    /** gender of new user must be m or f
     *
     */
    private char gender;

    /** Constructor of RegisterRequest object, sets values
     *
     */

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, char gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public RegisterRequest(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
