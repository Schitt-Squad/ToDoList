package todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is a concrete class of UIState. This class shows the Admin scene.
 * A listview displays Users. Admin can view Users, and change User passwords.
 *
 * @author Christian Liechty
 */

public class AdminState extends UIState{

    private static AdminState singleton;

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

        //Label
        Label userlist = new Label("User List");

        //Listview
        ListView users = new ListView();

        //Populate ListView with Users
        for (int i = 0; i < UserList.instance().size(); i++){
            //Observable List
            ObservableList<String> content = FXCollections.observableArrayList(UserList.instance().getUser(i).toString());
            users.setItems(content);
        }

        //Buttons
        Button changePassword = new Button("Change Password");

        //Action Event for Changing Password


        //Layout
        VBox left = new VBox(10, userlist, users);
        VBox right = new VBox(10, changePassword);
        right.setAlignment(Pos.CENTER_RIGHT);

        HBox both = new HBox(50, left, right);

        //Scene
        Scene adminScene = new Scene(both, 500, 500);

        //Stage
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin");
        adminStage.setScene(adminScene);
        adminStage.show();
    }
}
