package todolist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Braxton Grover
 */

public class User implements Serializable {
    //the long list of things related to the User, will need an array list of Lists
    private final int ID;
    private  final String firstName;
    private final String lastName;
    private String shortBio;
    private String emailAddress;
    private String password;
    private String userName;
    private ArrayList<TaskList> lists= new ArrayList<TaskList>();

    /**
     *
     * @param id     unique id number for a User
     * @param fName  first name
     * @param lName  last name
     * @param bio    biography of user
     * @param email  email address
     * @param pass   password
     * @param user   username
     */
    public User(int id, String fName, String lName, String bio, String email, String pass, String user){
        ID=id;
        firstName=fName;
        lastName=lName;
        shortBio=bio;
        emailAddress=email;
        password=pass;
        userName=user;
    }
    //Bio and Email are the only two that aren't vital to the functions of a User or secret so we can set them later
    public User(int id, String fName, String lName, String pass, String user){
        ID=id;
        firstName=fName;
        lastName=lName;
        shortBio=null;
        emailAddress=null;
        password=pass;
        userName=user;
    }

    //these first 3 don't change so we need only to get them to display them
    //getters and setters
    public int getID() {
        return ID;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    //only need getters for these to do logins
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    //Returns false in both of these for if for whatever reason the wrong value gets passed in.

    /**
     *
     * @param oldUser   current username
     * @param newUser   new username
     * @return          will return true if username change is successful
     */
    public boolean changeUserName(String oldUser, String newUser){
        if (oldUser == this.userName) {
            this.userName= newUser;
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * @param oldPass this is the previous password of the User
     * @param newPass this is the new password set by Admin
     * @return will return true if old password is updated to new password
     */
    public boolean changePassword(String oldPass, String newPass){
        if (oldPass == this.password) {
            this.password= newPass;
            return true;
        }else {
            return false;
        }
    }
    public void newList(String title, String description){
        if (description == null && title == null){
            lists.add(new TaskList());
        }else if (description == null){
            lists.add(new TaskList(title));
        } else {lists.add(new TaskList(title, description));}

    }

    public void removeList(TaskList list){ lists.remove(list); }

    public void changePassword(String newPass){ this.password = newPass; }

    public int getListArraySize(){ return lists.size(); }

    public TaskList getList(int index){ return lists.get(index); }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
