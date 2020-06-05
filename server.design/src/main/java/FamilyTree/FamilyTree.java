package FamilyTree;

import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Database.Database;
import FamilyData.Location;
import Model.Event;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FamilyTree {

    private int count = 0;
    private int year = 1980;
    private int originalGens;
    private User mainUser;
    private FamilyData.familyData familyData;
    private List<Person> bigFamily = new ArrayList<>();
    private List<Event> familyEvents = new ArrayList<>();

    public void generateTree(int numGenerations) {
        PersonNode root = new PersonNode(mainUser.getGender());
        root.setBirthYear(year);
        originalGens = numGenerations+1;
        createParents(numGenerations, root);
        try{
            addPersonsToDB(bigFamily);
            addEventsToDB(familyEvents);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void createParents(int numGenerations, PersonNode person) {
        Person newPerson = new Person();
        if (count == 0) {
            newPerson.setFirstName(mainUser.getFirstName());
            newPerson.setLastName(mainUser.getLastName());
            newPerson.setPersonID(mainUser.getPersonID());
            newPerson.setGender(person.gender);
            newPerson.setAssociatedUsername(mainUser.getUsername());
            person.personID = newPerson.getPersonID();
            createUserEvent(person);
        } else {
            newPerson.setAssociatedUsername(mainUser.getUsername());
            newPerson.setGender(person.gender);
            newPerson.setPersonID(person.getPersonID());
            newPerson.setSpouseID(person.spouse.getPersonID());
            person.personID = newPerson.getPersonID();
            if (person.gender.equals("m")) {
                newPerson.setFirstName(familyData.mNames.getRandom());
            } else {
                newPerson.setFirstName(familyData.fNames.getRandom());
            }
            newPerson.setLastName(familyData.sNames.getRandom());
        }
        count++;
        if ((numGenerations) != 0) {
            person.mother = new PersonNode("f");
            person.father = new PersonNode("m");
            person.father.setPersonID(Person.randomPersonID());
            person.mother.setPersonID(Person.randomPersonID());
            person.mother.spouse = person.father;
            person.father.spouse = person.mother;
            person.mother.setBirthYear(year - ((originalGens-numGenerations)*23));
            person.father.setBirthYear(person.father.spouse.birthYear-3);
            createMarriageEvent(person.mother,person.father);
            createDeathEvent(person.father);
            createDeathEvent(person.mother);
        } else {
            bigFamily.add(newPerson);
            createBirthEvent(person.birthYear,newPerson);
            return;
        }

        numGenerations--;
        createParents(numGenerations, person.mother);
        createParents(numGenerations, person.father);

        newPerson.setFatherID(person.father.personID);
        newPerson.setMotherID(person.mother.personID);
        createBirthEvent(person.birthYear,newPerson);
        if (person.spouse != null) {
            newPerson.setSpouseID(person.spouse.personID);
        }
        bigFamily.add(newPerson);
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public void setFamilyData(FamilyData.familyData familyData) {
        this.familyData = familyData;
    }

    private void printFamily() {
        for (int i = 0; i < bigFamily.size(); i++) {
            System.out.println(bigFamily.get(i));
        }
    }

    private void printEvents() {
        for (int i = 0; i < familyEvents.size(); i++) {
            System.out.println(familyEvents.get(i));
        }
    }

    private void createBirthEvent(int year, Person person){
        Location location = familyData.locations.getRandom();
        Event birth = new Event();
        birth.setAssociatedUsername(person.getAssociatedUsername());
        birth.setCity(location.getCity());
        birth.setCountry(location.getCountry());
        birth.setEventID(birth.randomEventID());
        birth.setEventType("Birth");
        birth.setLatitude(location.getLatitude());
        birth.setLongitude(location.getLongitude());
        birth.setYear(year);
        birth.setPersonID(person.getPersonID());

        familyEvents.add(birth);
    }

    private void createMarriageEvent(PersonNode mother, PersonNode father){
        Location location = familyData.locations.getRandom();
        int marriageYear = mother.getBirthYear()+20;
        Event marriage1 = new Event();
        marriage1.setPersonID(mother.getPersonID());
        marriage1.setYear(marriageYear);
        marriage1.setLongitude(location.getLongitude());
        marriage1.setLatitude(location.getLatitude());
        marriage1.setEventType("Marriage");
        marriage1.setEventID(marriage1.randomEventID());
        marriage1.setCountry(location.getCountry());
        marriage1.setCity(location.getCity());
        marriage1.setAssociatedUsername(mainUser.getUsername());
        Event marriage2 = new Event();
        marriage2.setPersonID(father.getPersonID());
        marriage2.setYear(marriageYear);
        marriage2.setLongitude(location.getLongitude());
        marriage2.setLatitude(location.getLatitude());
        marriage2.setEventType("Marriage");
        marriage2.setEventID(marriage2.randomEventID());
        marriage2.setCountry(location.getCountry());
        marriage2.setCity(location.getCity());
        marriage2.setAssociatedUsername(mainUser.getUsername());

        familyEvents.add(marriage1);
        familyEvents.add(marriage2);
    }

    private void createDeathEvent(PersonNode person){
        int deathYear = person.birthYear + 40;
        Location location = familyData.locations.getRandom();
        Event death = new Event();
        death.setPersonID(person.getPersonID());
        death.setYear(deathYear);
        death.setLongitude(location.getLongitude());
        death.setLatitude(location.getLatitude());
        death.setEventType("Death");
        death.setEventID(death.randomEventID());
        death.setCountry(location.getCountry());
        death.setCity(location.getCity());
        death.setAssociatedUsername(mainUser.getUsername());

        familyEvents.add(death);
    }

    private void createUserEvent(PersonNode person){
        int firstKissYear = person.birthYear + 13;
        Location location = familyData.locations.getRandom();
        Event firstKiss = new Event();
        firstKiss.setPersonID(person.getPersonID());
        firstKiss.setYear(firstKissYear);
        firstKiss.setLongitude(location.getLongitude());
        firstKiss.setLatitude(location.getLatitude());
        firstKiss.setEventType("First Kiss");
        firstKiss.setEventID(firstKiss.randomEventID());
        firstKiss.setCountry(location.getCountry());
        firstKiss.setCity(location.getCity());
        firstKiss.setAssociatedUsername(mainUser.getUsername());

        familyEvents.add(firstKiss);

        int gradYear = person.birthYear + 18;
        location = familyData.locations.getRandom();
        Event graduation = new Event();
        graduation.setPersonID(person.getPersonID());
        graduation.setYear(gradYear);
        graduation.setLongitude(location.getLongitude());
        graduation.setLatitude(location.getLatitude());
        graduation.setEventType("High School Graduation");
        graduation.setEventID(graduation.randomEventID());
        graduation.setCountry(location.getCountry());
        graduation.setCity(location.getCity());
        graduation.setAssociatedUsername(mainUser.getUsername());

        familyEvents.add(graduation);
    }

    private void addPersonsToDB(List<Person> persons) throws Exception{
        Database db = new Database();
        Connection conn = db.openConnection();
        PersonDAO personDAO = new PersonDAO(conn);

        for(Person person : persons){
            personDAO.createPerson(person);
        }

        db.closeConnection(true);

    }

    private void addEventsToDB(List<Event> events) throws Exception{
        Database db = new Database();
        Connection conn = db.openConnection();
        EventDAO eventDAO = new EventDAO(conn);
        for(Event event : events){
            eventDAO.createEvent(event);
        }
        db.closeConnection(true);
    }

}

