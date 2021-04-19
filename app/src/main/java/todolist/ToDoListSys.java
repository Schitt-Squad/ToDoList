package todolist;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Braxton Grover, Christian Liechty
 */

public class ToDoListSys {
    //I don't think there needs to be anything more here
    private UserList UserL = UserList.instance();
    private Admin admin = Admin.instance();
    private User currentUser;
    private static ToDoListSys singleton;
    private FileManager fileManager= new FileManager();

    //making it a singleton
    private ToDoListSys() {
        /*
        try {
            UserL.setUserList(fileManager.readFile("./User.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //for pulling from the file upon start up
        */

    }

    //Singleton Instance
    public static ToDoListSys instance() {
        if(singleton == null)
            singleton = new ToDoListSys();
        return singleton;
    }

    /**
     *
     * @param username  username of user attempting to login
     * @param password  password of user attempting to login
     * @return          will return true if credentials of user are verified
     */
    public boolean login(String username,  String password) {
        User user;
        for (int u= 1; u<UserL.size(); u++){
           user =  UserL.getUser(u);
           if (user.getUserName().equals(username)){
               if (user.getPassword().equals(password)){
                   currentUser= user;
                   return true;
               }
           }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    //Review this method
    public void logout(User user){
        currentUser = null;
    }

    public void newUser(String fName, String lName, String bio, String email, String pass, String user){
        UserL.addUser(fName, lName, bio, email, pass, user);
    }
    public ArrayList<User> getUserList(){
        return UserL.getUserList();
    }
}
