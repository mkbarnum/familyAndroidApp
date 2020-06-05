import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Database.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Request.EventRequest;
import Request.LoginRequest;
import Request.PersonRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.LoginResult;
import Result.PersonResult;
import Service.eventService;
import Service.loginService;
import Service.personService;
import Service.registerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class personServiceTest {
    Database db;
    PersonRequest personRequest = new PersonRequest();


    @Before
    public void setUp() throws Exception {
        db = new Database();

        RegisterRequest registerRequest = new RegisterRequest("mattyb","abc","matt@gmail.com","matt","barnum",'m');
        registerService registerService = new registerService();
        registerService.register(registerRequest);
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        String personID = userDAO.getUser("mattyb").getPersonID();
        personRequest.setUsername("mattyb");
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
    public void personPositive() {
        PersonResult result;
        personService pService = new personService();
        result = pService.findPerson(personRequest);
        List<Person> persons = result.getData();
        int size = 31;
        assertEquals(persons.size(),size);
        assertNotNull(result);
    }

    @Test
    public void personNegative() {
        PersonResult result;
        personService pService = new personService();
        personRequest.setPersonID("fakeID");
        result = pService.findPerson(personRequest);
        List<Person> persons = result.getData();
        assertNull(persons);
    }
}
