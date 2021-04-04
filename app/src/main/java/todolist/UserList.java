package todolist;

/**
 * This class handles the ArrayList of Users that comprise the application.
 * Here, users can be added to the list, found in the list, or removed from the list.
 *
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

    //instantiate userlist as a singleton
    public static UserList instance(){
        if (singleton==null){
            singleton = new UserList();
        }
        return singleton;
    }

    //add a new user, is a boolean for checking fields were properly filled out.

    /**
     *
     * @param fName first name of user being added to list
     * @param lName last name
     * @param bio   description of user's life
     * @param email email address
     * @param pass  password
     * @param user  username
     * @return      will return true if user is successfully added to UserList
     */
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

    /**
     *
     * @param user      takes in user to be removed
     * @param userID    user ID
     * @return          will return true if user is successfully removed from UserList
     */
    public boolean removeUser(User user, int userID){
        for (int i=0; i<userList.size(); i++) {
            if (userID == user.getID()) {
                userList.remove(user);
                return true;
            }
        }
        return false;

    }

    /**
     *
     * @return size of the UserList
     */
    public int size(){
        return userList.size();
    }

    /**
     *
     * @param index index of user in UserList
     * @return      user at specified index
     */
    public User getUser(int index){
        return userList.get(index);
    }
}
