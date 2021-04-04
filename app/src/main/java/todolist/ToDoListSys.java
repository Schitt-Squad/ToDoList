package todolist;

public class ToDoListSys {
    //I don't think there needs to be anything more here
    private UserList UserL = UserList.instance();
    private Admin admin = Admin.instance();
    private User currentUser;
    private static ToDoListSys singleton;

    //making it a singleton
    private ToDoListSys() {
    }

    //Singleton Instance
    public static ToDoListSys instance() {
        if(singleton == null)
            singleton = new ToDoListSys();
        return singleton;
    }

    //Review this method
    public void login(User user){
        currentUser = user;
    }

    //Review this method
    public void logout(User user){
        currentUser = null;
    }
}
