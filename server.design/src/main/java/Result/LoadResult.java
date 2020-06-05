package Result;

/** The LoadResult Object
 *
 */
public class LoadResult {
    /** The message that will be returned to the handler
     *
     */
    String message;
    /** Constructor that sets the message value
     *
     */
    public LoadResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
