package org.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String password;
    String filename="src/main/java/org/data/users.txt";
    private User currentUser;
    private ArrayList<User> users;

    public String getUserName() {
        return userName;
    }

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }


    public boolean login(String password){
        return this.password.equals(password);
    }


    public User(){
        this.users=new ArrayList<>();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean getUsers(){

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] objs=line.split(",");
                User user=new User(Integer.parseInt(objs[0]),objs[1],objs[2]);
                users.add(user);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return false;
        }
        finally {
            return true;
        }
    }
    public boolean loginUser(String username, String password){

        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserName().equalsIgnoreCase(username)){
                currentUser = users.get(i);
                return users.get(i).login(password);
            }
        }
        return false;
    }
}
