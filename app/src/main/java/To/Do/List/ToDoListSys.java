package To.Do.List;

public class ToDoListSys {
    //I don't think there needs to be anything more here
    private UserList UserL;
    private Admin admin;
    private User currentUser;

    //Review this method
    public void login(User user){
        currentUser = user;
    }

    //Review this method
    public void logout(User user){
        currentUser = null;
    }
}
