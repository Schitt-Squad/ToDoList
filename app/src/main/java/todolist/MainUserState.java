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
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class MainUserState extends UIState{

    private static MainUserState singleton;
    private FileManager fileMang= new FileManager();
    ListView<String> listsV = new ListView<String>();

    public static MainUserState instance(){
        if (singleton== null){
            singleton = new MainUserState();
        }
        return singleton;
    }

    private MainUserState() {
        //keeps access to the Main system
        super();

        //initializes new stage
        mainStage = new Stage();

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
        Label lists = new Label("Lists");
        ObservableList<String> listNames = FXCollections.observableArrayList();
        for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
            listNames.add(sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle());
        }
        listsV.setItems(listNames);
        listsV.setMaxSize(450, 800);
        listsV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //tasks
        Label tasks = new Label("Tasks");
        TableView<String> tasksV = new TableView<String>();
        TableColumn<Task, String> taskTitle = new TableColumn<>("Title");
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Task, LocalDate> taskDueDate = new TableColumn<>("Due Date");
        taskDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


            //buttons
            Button createList = new Button("Create List");
            Button deleteList = new Button("Delete List");
            Button createTask = new Button("Create Task");
            Button deleteTask = new Button("Delete Task");
            Button moveTask = new Button("Move Task");
            Button viewTask = new Button("ViewTask");
            Button logOut = new Button("Logout");

            //all for proper alignment, the first HBox is meant for the search items
            VBox vBoxOne = new VBox(lists, listsV, logOut);
            vBoxOne.setAlignment(Pos.CENTER_LEFT);
            vBoxOne.setSpacing(20);
            VBox vBoxTwo = new VBox(tasks, tasksV);
            vBoxTwo.setAlignment(Pos.CENTER_LEFT);
            vBoxTwo.setSpacing(10);
            VBox vBoxThree = new VBox(createList, deleteList, createTask, deleteTask, moveTask, viewTask);
            vBoxThree.setAlignment(Pos.CENTER);
            vBoxThree.setSpacing(20);
            VBox vBoxFour = new VBox();
            HBox hBoxOne = new HBox(vBoxOne, vBoxTwo, vBoxThree);
            hBoxOne.setSpacing(50);
            hBoxOne.setAlignment(Pos.BOTTOM_CENTER);
            HBox hBoxTwo = new HBox();

            currentScene = new Scene(hBoxOne, 800, 700);
            mainStage.setScene(currentScene);
            mainStage.show();

            createList.setOnMouseClicked(event -> {
                createItem();
            });

            logOut.setOnMouseClicked(event -> {
                mainStage.close();
                sys.logout();
            });

            listsV.setOnMouseClicked(event -> {
                tasksV.getItems().removeAll();
                TaskList currentList=null;
                for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
                    if (sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle().equals(listsV.getSelectionModel().getSelectedItem()) ) {
                        currentList = sys.getUserList().get(sys.getCurrentUser()).getList(i);
                    }
                }


                if (currentList.size()>0) {
                    tasksV.getItems().removeAll();
                    for (int i = 0; i < currentList.size(); i++) {
                        tasksV.getItems().add(currentList.getTask(i).getTitle());
                    }
                }
            });
        }

        //going to use this for Lists and create a seperate method for tasks
        @Override
        public void createItem () {
            Stage second = new Stage();

            Label titleL = new Label("Title");
            TextField title = new TextField();
            title.setText(null);
            title.setMinSize(100, 50);

            Label descriptionL = new Label("Description");
            TextField description = new TextField();
            description.setText(null);
            description.setMinSize(200, 300);
            description.setAlignment(Pos.TOP_LEFT);

            Button createList = new Button("Create List");
            Button cancel = new Button("Cancel");

            HBox buttons = new HBox(createList, cancel);
            buttons.setAlignment(Pos.BOTTOM_RIGHT);
            buttons.setSpacing(20);
            VBox titleLayout = new VBox(titleL, title);
            VBox descriptionLayout = new VBox(descriptionL, description);
            VBox finalLayout = new VBox(titleLayout, descriptionLayout, buttons);
            finalLayout.setAlignment(Pos.CENTER_RIGHT);
            finalLayout.setSpacing(50);

            second.setTitle("New List");
            second.setScene(new Scene(finalLayout, 600, 600));
            second.show();

            createList.setOnMouseClicked(event -> {
                if (title.getText() != null && description.getText() != null) {
                    TaskList newTaskList= new TaskList(title.getText(), description.getText());
                    sys.getUserList().get(sys.getCurrentUser()).newList(newTaskList);

                    second.close();

                    makePopUp();
                } else {
                    Stage third = new Stage();
                    Label nope = new Label("nope");
                    VBox err = new VBox(nope);
                    third.setScene(new Scene(err, 100, 100));
                    third.show();
                }

                //saves to the file
                try {
                    fileMang.writeUser("./User.json", sys.getUserList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ObservableList<String> listNames = FXCollections.observableArrayList();
                for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
                    listNames.add(sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle());
                }
                listsV.setItems(listNames);



            });

            cancel.setOnMouseClicked(event -> {
                second.close();

            });

        }

        @Override
        public void makePopUp () {
            Stage second = new Stage();

            Label success = new Label("Item Was Created Successfully");
            Button oK = new Button("OK!");
            VBox suc = new VBox(success, oK);
            suc.setAlignment(Pos.CENTER);
            suc.setSpacing(10);
            second.setScene(new Scene(suc, 200, 100));
            oK.setOnMouseClicked(event -> {
                second.close();
            });
        }


    }

