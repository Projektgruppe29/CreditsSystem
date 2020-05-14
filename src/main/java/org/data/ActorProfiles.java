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
}
