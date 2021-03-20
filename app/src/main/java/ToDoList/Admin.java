package ToDoList;

public class Admin {
    //Instance Variables
    private String username;
    private String password;
    private static Admin singleton;

    //Default private constructor
    private Admin() {}

    //Constructor
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Singleton Instance
    public static Admin instance() {
        if(singleton == null)
            singleton = new Admin();
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

    //Allows Admin to change User password
    public boolean ChangeUserPassword(String pass){
        return true;
    }
}
