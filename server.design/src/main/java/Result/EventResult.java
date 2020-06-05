package Result;

import Model.Event;

import java.util.List;

/** The EventResult Object
 *
 */

public class EventResult {
    /** the Event that will be returned to handler
     *
     */
    Event foundEvent;
    /** The list of events that will be returned to handler
     *
     */
    List<Event> data;
    /** the message that will be returned to handler
     *
     */
    String message;
    /** Constructor that sets mainEvent value
     *
     */

    public EventResult(Event mainEvent) {
        this.foundEvent = mainEvent;
    }
    /** Constructor that sets listEvents value
     *
     */
    public EventResult(List<Event> events) {
        this.data = events;
    }
    /** Constructor that sets message value
     *
     */
    public EventResult(String message) {
        this.message = message;
    }

    public Event getMainEvent() {
        return foundEvent;
    }

    public void setMainEvent(Event mainEvent) {
        this.foundEvent = mainEvent;
    }

    public List<Event> getEvents() {
        return data;
    }

    public void setEvents(List<Event> events) {
        this.data = events;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
