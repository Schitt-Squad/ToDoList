package todolist;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is a concrete class of UIState. This class shows the Create List scene.
 * A User will enter a title and description of the list they want to create. The list can
 * either be created or cancelled.
 *
 * @author Christian Liechty
 */

public class CreateListState extends UIState{

    private static CreateListState singleton;

    //instantiate CreateListState as a singleton
    public static CreateListState instance(){
        if (singleton == null){
            singleton = new CreateListState();
        }
        return singleton;
    }

    private CreateListState() {
        //initializing the system
        super();

        //Initiate Stage
        Stage listStage = new Stage();

        //Labels
        Label title = new Label("Title");
        Label description = new Label("Description");

        //Textfields
        TextField textTitle = new TextField();
        TextField textDesc = new TextField();

        //Buttons
        Button create = new Button("Create List");
        Button cancel = new Button("Cancel");

        //Button Actions
        //cancels new list creation
        cancel.setOnMouseClicked(event -> {
            listStage.close();
        });

        //creates new List
        create.setOnMouseClicked(event -> {
            if (textTitle.getText() != null && textDesc.getText() != null) {
                new TaskList(textTitle.getText(), textDesc.getText());
                listStage.close();
            } else{
                this.makePopUp();
            }
        });

        //Layout
        VBox first = new VBox(10, title, textTitle, description, textDesc);
        HBox second = new HBox(10, create, cancel);
        VBox design = new VBox(first, second);

        //Positioning
        second.setAlignment(Pos.BOTTOM_RIGHT);

        //Scene
        Scene listScene = new Scene(design, 500, 500);

        //Set up Stage
        listStage.setTitle("Create List");
        listStage.setScene(listScene);
        listStage.show();
    }
}
