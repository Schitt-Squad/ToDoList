package todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;

public class MainUserState extends UIState{

    private static MainUserState singleton;

    public static MainUserState instance(){
        if (singleton== null){
            singleton = new MainUserState();
        }
        return singleton;
    }

    private MainUserState(){
        //keeps access to the Main system
        super();

        //initializes new stage
        mainStage= new Stage();

        //search items
        /* did not have time to implement
        TextField searchT= new TextField();
        searchT.setMaxSize(150, 60);
        Button search= new Button("Search");
        RadioButton allS= new RadioButton("All");
        RadioButton listsS= new RadioButton("Lists");
        RadioButton tasksS= new RadioButton("Tasks");
        RadioButton labelS= new RadioButton("Labels");
        RadioButton archived= new RadioButton("Archived");
         */


        //lists
        Label lists= new Label("Lists");
        ListView<String> listsV= new ListView<String>();
        ObservableList<String> listNames= FXCollections.observableArrayList();
        for (int i=0; i<sys.getCurrentUser().getListArraySize(); i++){
           listNames.add(sys.getCurrentUser().getList(i).getTitle());
        }
        listsV.setItems(listNames);
        listsV.setMaxSize(450, 800);

        //tasks
        Label tasks= new Label("Tasks");
        TableView<String> tasksV= new TableView<String>();
        TableColumn<Task, String> taskTitle= new TableColumn<>("Title");
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Task, Calendar> taskDueDate= new TableColumn<>("Due Date");
        taskDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TaskList currentList= null;
        for (int i=0; i<sys.getCurrentUser().getListArraySize(); i++){
            if (sys.getCurrentUser().getList(i).getTitle() == listsV.getOnMouseClicked().toString()){
                currentList= sys.getCurrentUser().getList(i);
            }
        }


        if (currentList!= null) {
            for (int i = 0; i < currentList.size(); i++) {
                tasksV.getItems().add(currentList.getTask(i).getTitle());
            }
        }



        //buttons
        Button createList= new Button("Create List");
        Button deleteList= new Button("Delete List");
        Button createTask= new Button("Create Task");
        Button deleteTask= new Button("Delete Task");
        Button moveTask= new Button("Move Task");
        Button viewTask= new Button("ViewTask");

        //all for proper alignment, the first HBox is meant for the search items
        VBox vBoxOne= new VBox(lists, listsV);
        vBoxOne.setAlignment(Pos.CENTER_LEFT);
        vBoxOne.setSpacing(10);
        VBox vBoxTwo= new VBox(tasks, tasksV);
        vBoxTwo.setAlignment(Pos.CENTER_LEFT);
        vBoxTwo.setSpacing(10);
        VBox vBoxThree= new VBox(createList, deleteList, createTask, deleteTask, moveTask, viewTask);
        vBoxThree.setAlignment(Pos.CENTER);
        vBoxThree.setSpacing(20);
        VBox vBoxFour= new VBox();
        HBox hBoxOne= new HBox(vBoxOne, vBoxTwo, vBoxThree);
        hBoxOne.setSpacing(20);
        hBoxOne.setAlignment(Pos.BOTTOM_CENTER);
        HBox hBoxTwo= new HBox();

        currentScene= new Scene(hBoxOne, 800, 1000);
        mainStage.setScene(currentScene);
        mainStage.show();

    }



}
