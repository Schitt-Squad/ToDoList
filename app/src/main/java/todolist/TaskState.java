package todolist;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * @author Christian Liechty
 */
public class TaskState {

    private static TaskState singleton;

    //instantiate TaskState as a singleton
    public static TaskState instance(){
        if (singleton == null){
            singleton = new TaskState();
        }
        return singleton;
    }

    private TaskState(){
        //initializing the system
        super();

        //Initiate Stage
        Stage taskStage = new Stage();

        //Labels
        Label title = new Label("Title");
        Label description = new Label("Description");
        Label labelDueDate = new Label("Due Date");
        Label labelPriority = new Label("Priority");
        Label label = new Label("Label");

        //Textfields
        TextField tTitle = new TextField();
        TextField tDescription = new TextField();
        TextField tLabel = new TextField();

        //DatePicker
        DatePicker dueDate = new DatePicker();

        //Use this for getting value from DatePicker
        //LocalDate value = dueDate.getValue();

        //ChoiceBox for selecting priority of tasks
        ChoiceBox priorityChoice = new ChoiceBox();

        //Add options to ChoiceBox
        priorityChoice.getItems().add("1");
        priorityChoice.getItems().add("2");
        priorityChoice.getItems().add("3");
        priorityChoice.getItems().add("4");
        priorityChoice.getItems().add("5");

        //Buttons
        //Buttons
        Button create = new Button("Create Task");
        Button cancel = new Button("Cancel");

        //Button Actions
        //cancels new list creation
        cancel.setOnMouseClicked(event -> {
            taskStage.close();
        });


    }
}
