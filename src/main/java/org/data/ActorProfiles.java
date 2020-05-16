package org.data;

public class ActorProfiles {

    private String fullname;
    private String role;
    private int personID;

    ActorProfiles(String fullname, int personID, String role) {
        this.fullname = fullname;
        this.personID = personID;
        this.role = role;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getPersonID() {
        return personID;
    }
}
