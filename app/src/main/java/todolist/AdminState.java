package todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is a concrete class of UIState. This class shows the Admin scene.
 * A listview displays Users. Admin can view Users, and change User passwords.
 *
 * @author Christian Liechty
 */

public class AdminState extends UIState{

    private FileManager fileMang= new FileManager();
    private static AdminState singleton;
    //Listview
    ListView users = new ListView();

    //instantiate AdminState as a singleton
    public static AdminState instance(){
        if (singleton == null){
            singleton = new AdminState();
        }
        return singleton;
    }

    private AdminState() {
        //initializing the system
        super();

        Stage adminStage = new Stage();

        //Label
        Label userlist = new Label("User List");

        //Populate ListView with Users
        for (int i = 0; i < UserList.instance().size(); i++){
            //Observable List
            ObservableList<String> content = FXCollections.observableArrayList(UserList.instance().getUser(i).toString());
            users.setItems(content);
        }

        //Buttons
        Button changePassword = new Button("Change Password");
        Button logOut = new Button("Logout");

        //Action Event for Changing Password
        changePassword.setOnMouseClicked(event -> {
            this.password();
        });

        //Action Event for Logout
        logOut.setOnMouseClicked(event -> {
            adminStage.close();
        });


        //Layout
        VBox left = new VBox(10, userlist, users);
        VBox right = new VBox(10, changePassword, logOut);
        right.setAlignment(Pos.CENTER_RIGHT);

        HBox both = new HBox(50, left, right);

        //Scene
        Scene adminScene = new Scene(both, 500, 500);

        //Stage
        adminStage.setTitle("Admin");
        adminStage.setScene(adminScene);
        adminStage.show();
    }

    public void password(){
        //Initiate Stage
        Stage passwordStage = new Stage();

        //Label
        Label newPass = new Label("New Password");

        //TextField
        TextField pass = new TextField();

        //Buttons
        Button changePass = new Button("Change Password");
        Button cancel = new Button("Cancel");

        //Button Actions
        cancel.setOnMouseClicked(event -> {
            passwordStage.close();
        });

        //Button action for changing password
        changePass.setOnMouseClicked(event -> {

            for(int i = 0; i < UserList.instance().size(); i++){
                sys.getUserList().get(users.getSelectionModel().getSelectedIndex()).changePassword(pass.getText());
            }

            try {
                fileMang.writeUser("./User.json", sys.getUserList());
            } catch (IOException e) {
                e.printStackTrace();
            }

            passwordStage.close();

        });

        //Layout
        VBox first = new VBox(newPass, pass);
        HBox second = new HBox(changePass, cancel);
        VBox third = new VBox(first, second);

        currentScene = new Scene(third, 500, 500);
        passwordStage.setScene(currentScene);
        passwordStage.show();
    }
}
