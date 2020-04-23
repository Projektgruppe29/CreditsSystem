package org.domain;

import java.util.ArrayList;

public class Credits {

    private String fullName;
    private String role;
    private int personID; //kan ændres til long afhængig af størrelsen

    private String nameOfProduction;
    private int productionID;


    ArrayList<String> Credits;


    public Credits(String fullName, int personID, String role) {
        this.fullName = fullName;
        this.personID = personID;
        this.role = role;
    }


    public void Production(String ProductionName, int ProductionID) {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPersonID() {
        return personID;
    }

    public String getRole() {
        return role;
    }

}


