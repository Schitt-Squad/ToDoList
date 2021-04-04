package todolist;

/**
 * @author Braxton Grover
 */

import java.util.ArrayList;

public class UserList {
    //local variables needs to add admin
    private ArrayList<User> userList= new ArrayList<User>();
    private static UserList singleton;
    private int idCounter;

    //need to set ID Counter for giving users unique IDs
    private UserList(){
        idCounter=1;
    }
    public static UserList instance(){
        if (singleton==null){
            singleton = new UserList();
        }
        return singleton;
    }

    //add a new user, is a boolean for checking fields were properly filled out.
    public boolean addUser(String fName, String lName, String bio, String email, String pass, String user){
        if(bio != null && email != null) {
            User newUser = new User(idCounter, fName, lName, bio, email, pass, user);
            userList.add(newUser);
            idCounter++;
            return true;

        } else if(bio == null && email == null){
            User newUser = new User(idCounter, fName, lName, pass, user);
            userList.add(newUser);
            idCounter++;
            return true;

        } else {
            return false;
        }

    }

    //takes out a user and checks user ID for validity. May add in password as a check as well.
    public boolean removeUser(User user, int userID){
        for (int i=0; i<userList.size(); i++) {
            if (userID == user.getID()) {
                userList.remove(user);
                return true;
            }
        }
        return false;

    }
    public int size(){
        return userList.size();
    }
    public User getUser(int index){
        return userList.get(index);
    }
}
