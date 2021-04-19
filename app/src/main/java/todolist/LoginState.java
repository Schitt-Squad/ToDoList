package todolist;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Braxton Grover, Christian Liechty
 */

public class LoginState extends UIState {

    private static LoginState singleton;
    private FileManager fileManager= new FileManager();

    //for using the System
    private LoginState() {



        //initializing system
        super();
        //laying out login screen
        Label loginLabel= new Label("LOGIN");
        loginLabel.setFont(Font.font(24));

        Label userLabel= new Label("Username:");
        userLabel.setFont(Font.font(16));

        Label passLabel= new Label("Password:");
        passLabel.setFont(Font.font(16));

        TextField userText= new TextField();
        PasswordField passText= new PasswordField();

        Button loginButt= new Button();
        loginButt.setText("Login");

        Hyperlink newUser= new Hyperlink("Create New User");

        Hyperlink adminLogin = new Hyperlink("Admin Login");

        VBox loginOne= new VBox(userLabel, userText, passLabel, passText);
        VBox loginTwo= new VBox(loginLabel, loginOne, loginButt, newUser, adminLogin);
        //alignment for the VBoxes
        loginOne.setSpacing(10);
        loginTwo.setSpacing(50);
        loginOne.setAlignment(Pos.CENTER);
        loginTwo.setAlignment(Pos.CENTER);

        //setting the scene and showing it.
        currentScene= new Scene(loginTwo, 500, 500);
        mainStage= new Stage();
        mainStage.setTitle("Login");
        mainStage.setScene(currentScene);

        //handles login
        loginButt.setOnMouseClicked(event -> {
            if (sys.login(userText.getText(), passText.getText()))
            {
                mainStage.close();
               Stage login= MainUserState.instance().getStage();
               login.show();

            }else {
                this.makePopUp();
            }
        });

        //handles making a new user
        newUser.setOnMouseClicked(event -> {
            this.createItem();
        });

        //Admin View
        adminLogin.setOnMouseClicked(event -> {
            this.createAdmin();
        });

    }

    public static LoginState instance(){
        if (singleton == null){
            singleton = new LoginState();
        }
        return singleton;
    }


    //for creating a user
    @Override
    public void createItem(){
        Stage newUserStage= new Stage();

        Label username= new Label("Username:");
        TextField userT= new TextField();
        userT.setText(null);
        userT.setMaxSize(150, 60);

        Label password= new Label("Password:");
        TextField passT= new TextField();
        passT.setText(null);
        passT.setMaxSize(150, 60);

        Label email= new Label("Email: ");
        TextField mailT= new TextField();
        mailT.setMaxSize(150, 60);

        Label fName= new Label("First Name: ");
        TextField fNameT= new TextField();
        fNameT.setText(null);
        fNameT.setMaxSize(150, 60);

        Label lName= new Label("Last Name: ");
        TextField lNameT= new TextField();
        lNameT.setText(null);
        lNameT.setMaxSize(150, 60);

        Label bio= new Label("Biography: ");
        TextField bioT= new TextField();
        bioT.setMinSize(400, 300);
        bioT.setAlignment(Pos.TOP_LEFT);

        Button cancel= new Button("Cancel");
        Button create= new Button("Create");

        HBox hBox= new HBox(create, cancel);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setSpacing(20);
        VBox newUserBox= new VBox(fName, fNameT, lName, lNameT, username, userT, password, passT, email, mailT, bio, bioT, hBox);
        newUserBox.setAlignment(Pos.CENTER_LEFT);
        newUserBox.setSpacing(10);
        Scene newUserScene= new Scene(newUserBox, 500, 700);

        newUserStage.setTitle("Create New User");
        newUserStage.setScene(newUserScene);
        newUserStage.show();

        //creates the new user and adds it to the list
        create.setOnMouseClicked(event -> {
            if (fNameT.getText() != null && lNameT.getText() != null && userT.getText() != null && passT.getText() != null) {
                sys.newUser(fNameT.getText(), lNameT.getText(), bioT.getText(), mailT.getText(), passT.getText(), userT.getText());

                try {
                    fileManager.writeUser("./User.json", sys.getUserList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //for saving to the file

                newUserStage.close();

            } else{
                this.makePopUp();
            }
        });

        //cancels new user creation and closes extra window
        cancel.setOnMouseClicked(event -> {
            newUserStage.close();
        });


    }

    //an Error message
    @Override
    public void makePopUp(){
        Stage errLogStag= new Stage();
        errLogStag.setTitle("Login Failure");
        Label errLogLab= new Label("Something was incorrect or you entered nothing");
        Scene errLogScen= new Scene(errLogLab, 350, 100);
        errLogStag.setScene(errLogScen);
        errLogStag.show();
    }

    /**
     * After the "Admin Login" hyperlink is clicked by the User, the scene will transition to Admin login.
     * If "admin" and "password" are entered the Admin is able to login and change the passwords of Users.
     * 
     * @author Christian Liechty
     */
    //transition to Admin Login
    public void createAdmin() {
        //Labels
        Label loginLabel= new Label("ADMIN LOGIN");
        loginLabel.setFont(Font.font(24));

        Label userLabel= new Label("Username:");
        userLabel.setFont(Font.font(16));

        Label passLabel= new Label("Password:");
        passLabel.setFont(Font.font(16));

        //Textfields
        TextField userText= new TextField();
        PasswordField passText= new PasswordField();

        //Tooltip
        Tooltip tip = new Tooltip("Username: admin \nPassword: password");

        //Button
        Button loginButt= new Button();
        loginButt.setText("Login");
        loginButt.setTooltip(tip);

        //Button Action
        //handles login
        loginButt.setOnMouseClicked(event -> {
            if (userText.getText().equals("admin") && passText.getText().equals("password"))
            {
                mainStage.close();
                App.currentState = AdminState.instance();

            }else {
                this.makePopUp();
            }
        });

        //Layout
        VBox loginOne= new VBox(userLabel, userText, passLabel, passText);
        VBox loginTwo= new VBox(loginLabel, loginOne, loginButt);
        //alignment for the VBoxes
        loginOne.setSpacing(10);
        loginTwo.setSpacing(50);
        loginOne.setAlignment(Pos.CENTER);
        loginTwo.setAlignment(Pos.CENTER);

        currentScene = new Scene(loginTwo, 500, 500);
        mainStage= new Stage();
        mainStage.setTitle("Admin Login");
        mainStage.setScene(currentScene);
        mainStage.show();
    }

}
