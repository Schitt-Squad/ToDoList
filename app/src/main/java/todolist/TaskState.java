package todolist;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 *
 * @author Christian Liechty
 */
public class TaskState extends UIState {

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

        //ChoiceBox for selecting priority of tasks
        ChoiceBox priorityChoice = new ChoiceBox();

        //Add options to ChoiceBox
        priorityChoice.getItems().add(1);
        priorityChoice.getItems().add(2);
        priorityChoice.getItems().add(3);
        priorityChoice.getItems().add(4);
        priorityChoice.getItems().add(5);

        //Buttons
        Button create = new Button("Create Task");
        Button cancel = new Button("Cancel");

        //Button Actions
        /**
         * When the "Cancel" button is clicked the stage will close
         * and a new task will not be created
         */
        cancel.setOnMouseClicked(event -> {
            taskStage.close();
        });

        //Button action for creating task
        create.setOnMouseClicked(event -> {
            //Use this for getting value from DatePicker
            LocalDate value = dueDate.getValue();

            if (tTitle.getText() != null && tDescription.getText() != null){
                new Task((Integer) priorityChoice.getValue(), tTitle.getText(),
                        tDescription.getText(), value, tLabel.getText());
            } else {
                this.makePopUp();
            }
        });

        //Layout
        VBox first = new VBox(title, tTitle, description, tDescription, labelDueDate, dueDate);
        VBox second = new VBox(labelPriority, priorityChoice, label, tLabel);
        HBox third = new HBox(create, cancel);
        HBox fourth = new HBox(first, second);
        VBox fifth = new VBox(fourth, third);

        //Scene
        Scene taskScene = new Scene(fifth, 500, 500);

        //Set Stage
        taskStage.setTitle("Create Task");
        taskStage.setScene(taskScene);
        taskStage.show();


    }
}
