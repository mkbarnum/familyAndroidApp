import Database.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Service.loadService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class loadServiceTest {
    Database db;
    LoadRequest loadRequest = new LoadRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();
        Event event = new Event("abc123" , "mkbarnum" ,"matt",-23f,32.8f,"abc","abcd","abcde",2012);
        Person person = new Person("abc123" , "mkbarnum" ,"matt","barnum","m","abc","abcd","abcde");
        User user = new User("mkbarnum", "abc", "mattyb@gmail.com", "matthew", "barnum", "m", "123456");
        List<Event> eventList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        eventList.add(event);
        personList.add(person);
        userList.add(user);
        loadRequest.setEvents(eventList);
        loadRequest.setPersons(personList);
        loadRequest.setUsers(userList);
        RegisterRequest registerRequest = new RegisterRequest("mattyb","abc","matt@gmail.com","matt","barnum",'m');
        registerService registerService = new registerService();
        registerService.register(registerRequest);
        db.openConnection();
        db.createTables();
        db.closeConnection(true);
    }

    @After
    public void destroy() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loadPositive() {
        LoadResult result;
        loadService lService = new loadService();
        result = lService.load(loadRequest);
        String compareMessage = "Successfully added 1 users, 1 persons, and 1 events to the database.";
        assertNotNull(result);
        assertEquals(compareMessage,result.getMessage());
    }

    @Test
    public void loadNegative() {
        LoadResult result;
        loadService lService = new loadService();
        result = lService.load(loadRequest);
        String compareMessage = "Sucessfully added 1 users, 1 persons, and 1 events to the database.";
        assertNotNull(result);
        assertNotEquals(compareMessage,result.getMessage());
    }
}
