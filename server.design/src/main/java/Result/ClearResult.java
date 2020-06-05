package Result;

/** The ClearResult Object
 *
 */

public class ClearResult {
    /** Message that will be returned to the Handler
     *
     */
    String message;

    /** Constructor of the ClearResult, sets values
     *
     */
    public ClearResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
