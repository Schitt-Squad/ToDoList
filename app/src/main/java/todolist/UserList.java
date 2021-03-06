package todolist;

/**
 * This class handles the ArrayList of Users that comprise the application.
 * Here, users can be added to the list, found in the list, or removed from the list.
 *
 * @author Braxton Grover
 */

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {
    //local variables needs to add admin
    private ArrayList<User> userList= new ArrayList<User>();
    private static UserList singleton;

    //need to set ID Counter for giving users unique IDs
    private UserList(){

    }

    //instantiate userlist as a singleton
    public static UserList instance(){
        if (singleton==null){
            singleton = new UserList();
        }
        return singleton;
    }

    /**
     * Method for adding user
     *
     * @param fName first name of user being added to list
     * @param lName last name
     * @param bio   description of user's life
     * @param email email address
     * @param pass  password
     * @param user  username
     * @return      will return true if user is successfully added to UserList
     */
    public boolean addUser(int id, String fName, String lName, String bio, String email, String pass, String user){
        if(bio != null && email != null) {
            User newUser = new User(id, fName, lName, bio, email, pass, user);
            userList.add(newUser);

            return true;

        } else if(bio == null && email == null){
            User newUser = new User(id, fName, lName, pass, user);
            userList.add(newUser);
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
    public ArrayList<User> getUserList(){
        return userList;
    }
    public void setUserList(ArrayList<User> users){
        userList= users;

    }
}
