package Request;

/** The EventRequest Object
 *
 */

public class EventRequest {

    /** The eventID attached to the request
     */
    String eventID;
    String username;

    /** Creates an instance of the EventRequest Object
     */
    public EventRequest() {}

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
