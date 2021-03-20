package ToDoList;

import java.util.ArrayList;

public class UserList {
    //local variables needs to add admin
    private ArrayList<User> userList= new ArrayList<User>();
    private static UserList singleton;

    private UserList(){

    }
    public static UserList instance(){
        if (singleton==null){
            singleton = new UserList();
        }
        return singleton;
    }

    //add a new user
    public void addUser(){

    }

    //takes out a user
    public void removeUser(){

    }



}
