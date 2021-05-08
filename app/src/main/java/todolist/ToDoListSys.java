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
    private int currentUser;
    private static ToDoListSys singleton;
    private FileManager fileManager= new FileManager();
    private int idCounter;

    //making it a singleton


        //pulls in User ArrayList to be dropped into the main UserList
        private ToDoListSys(String file) {
            ArrayList<User> temp;

            try {
                temp =fileManager.readUser(file);
                UserL.setUserList(temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            idCounter= UserL.size();

            //for pulling from the file upon start up


        }

        //Singleton Instance
        public static ToDoListSys instance(String file) {
            if(singleton == null)
                singleton = new ToDoListSys(file);
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
            for (int u= 0; u<UserL.size(); u++){
                user =  UserL.getUser(u);
                if (user.getUserName().equals(username)){
                    if (user.getPassword().equals(password)){
                        currentUser= u;
                        return true;
                    }
                }
            }
            return false;
        }

        public int getCurrentUser() {
            return currentUser;
        }

        //Review this method
        public void logout(){
            try {
                fileManager.writeUser("./User.json", UserL.getUserList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //for saving to the file
            currentUser = -1;
        }

        public void newUser(String fName, String lName, String bio, String email, String pass, String user){
            UserL.addUser(idCounter+1, fName, lName, bio, email, pass, user);
        }
        public ArrayList<User> getUserList(){
            return UserL.getUserList();
        }
    }
