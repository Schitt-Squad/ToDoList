package ToDoList;

public class User {
    //the long list of things related to the User, will need an array list of Lists
    private int ID;
    private String firstName;
    private String lastName;
    private String shortBio;
    private String emailAddress;
    private String password;
    private String userName;

    //set everything up by passing everything into the constructor
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
    public boolean changeUserName(String oldUser, String newUser){
       if (oldUser == this.userName) {
           this.userName= newUser;
           return true;
       }else {
           return false;
       }
    }

    public boolean changePassword(String oldPass, String newPass){
        if (oldPass == this.password) {
            this.password= newPass;
            return true;
        }else {
            return false;
        }
    }

    public void changePassword(String newPass){
        this.password = newPass;
    }
}
