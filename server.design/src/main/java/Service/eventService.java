package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Database.Database;
import Model.Event;
import Request.EventRequest;
import Result.EventResult;

import java.sql.Connection;
import java.util.List;

/** Service for event method
 *
 */

public class eventService {

    /** Returns event objects that are being searched for
     *
     * @param r The EventRequest object that is being looked for
     * @return the EventResult object
     */
    public static EventResult findEvent(EventRequest r){

        EventResult result = new EventResult("");
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            EventDAO eventDAO = new EventDAO(conn);


            if (r.getEventID() == null) {
                String username = r.getUsername();
                List<Event> events = eventDAO.getByUsername(username);
                result.setEvents(events);
                result.setMessage(null);
            } else {
                String eventID = r.getEventID();
                Event event = eventDAO.getEvent(eventID);
                if(event == null){
                    result.setMessage("EventID not found in Database");
                }
                else if(!event.getAssociatedUsername().equals(r.getUsername())){
                    result.setMessage("Event does not belong to this user");
                }
                else {
                    result.setMainEvent(event);
                    result.setMessage(null);
                }
            }
            db.closeConnection(true);
        } catch(DataAccessException e){
            result.setMessage("DataAccess error!");
            e.printStackTrace();
        }


        return result;
    }
}
