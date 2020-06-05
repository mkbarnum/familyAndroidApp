package Model;

import java.util.Objects;
import java.util.UUID;

/** AuthToken Object
 *
 */

public class AuthToken {
    /** the Username attached to the AuthToken
     * */
    private String username;

    /** the unique authToken
     * */

    private String authToken;

    /** Sets values
     *
     * @param username username of the user
     * @param authToken the unique authToken attached to the user
     * */

    public AuthToken(String authToken, String username) {
        this.username = username;
        this.authToken = authToken;
    }

    /** Creates an instance without any values
     * */

    public AuthToken() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }

    public static String generateAuthToken(){
        String authToken = UUID.randomUUID().toString();
        return authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken token = (AuthToken) o;
        return Objects.equals(username, token.username) &&
                Objects.equals(authToken, token.authToken);
    }

}
