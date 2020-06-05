package Result;

import Model.Person;

import java.util.List;

/** PersonResult Object
 *
 */
public class PersonResult {
    /** username associated with person
     *
     */
    String associatedUsername;
    /** personid of the person
     *
     */
    String personID;

    /** firstname of the person
     *
     */
    String firstName;
    /** lastname of the person
     *
     */
    String lastName;
    /** gender of the person
     *
     */
    String gender;
    /** fatherID of the person
     *
     */
    String fatherID;
    /** motherID of the person
     *
     */
    String motherID;
    /** spouseID of the person
     *
     */
    String spouseID;
    /** message returned to the handler
     *
     */
    String message;
    /** a list of persons
     *
     */
    List<Person> data;

    Person foundPerson;
    /** Constructor of PersonResult, sets value
     *
     */
    public PersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    /** Constructor that sets message value
     *
     */
    public PersonResult(String message) {
        this.message = message;
    }
    /** Constructor that sets list value
     *
     */

    public PersonResult(List<Person> data) {
        this.data = data;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public Person getFoundPerson() {
        return foundPerson;
    }

    public void setFoundPerson(Person foundPerson) {
        this.foundPerson = foundPerson;
    }
}

