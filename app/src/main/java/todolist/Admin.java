package todolist;

/**
 * @author Christian Liechty
 */

public class Admin {
    //Instance Variables
    private String username;
    private String password;
    private String userPassword;
    private static Admin singleton;

    //Constructor

    /**
     *
     * @param username Username for Admin
     * @param password Password for Admin
     */
    private Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Singleton Instance
    public static Admin instance() {
        if(singleton == null)
            singleton = new Admin("admin", "password");
        return singleton;
    }

    //Define getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    //Allows Admin to change Admin password
    public boolean changeAdminPassword(String oldPass, String newPass){
        if(oldPass == this.password) {
            this.password = newPass;
            return true;
        }
        else
            return false;
    }

    //Allows Admin to change User password
    public boolean changeUserPassword(User user){
        user.changePassword(userPassword);
        return true;
    }
}
