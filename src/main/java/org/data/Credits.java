package org.data;

public class Credits {
    private int id;
    private int castId;
    private String name;
    private String role;

    public Credits(int id, int cast_id, String name, String role) {
        this.id = id;
        this.castId = cast_id;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getCastID() {
        return castId;
    }
}


