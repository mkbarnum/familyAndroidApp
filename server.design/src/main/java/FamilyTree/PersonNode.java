package FamilyTree;

import Model.Person;

public class PersonNode {
    public PersonNode father;
    public PersonNode mother;
    public PersonNode spouse;
    public String personID = null;
    public String gender;
    public int birthYear;

    public PersonNode(String gender) {
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
