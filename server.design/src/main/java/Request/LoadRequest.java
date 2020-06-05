package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.List;

/** The LoadRequest Object
 *
 */

public class LoadRequest {

    /** A List of users to be added into database
     *
     */
    private List<User> users;
    /** A list of persons to be added into database
     *
     */
    private List<Person> persons;
    /** A list of events to be added into database
     *
     */
    private List<Event> events;

    /** Constructor for LoadRequest object, sets valeus
     *
     */

    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public LoadRequest(){}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "LoadRequest{" +
                "users=" + users +
                ", persons=" + persons +
                ", events=" + events +
                '}';
    }
}
