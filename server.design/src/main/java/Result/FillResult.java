package Result;

/** The FillResult Object
 *
 */

public class FillResult {
    /** The message that will be sent to the handler
     *
     */
    String message;
    /** Constructor that sets the message value
     *
     */
    public FillResult(String message) {
        this.message = message;
    }

    public FillResult(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
