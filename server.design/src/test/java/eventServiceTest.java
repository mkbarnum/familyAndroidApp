import Database.Database;
import Model.Event;
import Request.EventRequest;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.EventResult;
import Service.clearService;
import Service.eventService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class eventServiceTest {
    Database db;
    EventRequest eventRequest = new EventRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();
        eventRequest.setUsername("mattyb");
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
    public void eventPositive() {
        EventResult result;
        eventService eService = new eventService();
        result = eService.findEvent(eventRequest);
        List<Event> events = result.getEvents();
        int size = 93;
        assertEquals(events.size(),size);
        assertNotNull(result);
    }

    @Test
    public void eventNegative() {
        EventResult result;
        eventService eService = new eventService();
        eventRequest.setEventID("fakeID");
        result = eService.findEvent(eventRequest);
        List<Event> events = result.getEvents();
        int size = 93;
        assertNull(events);
    }

}
