package todolist;

import com.sun.jdi.connect.Connector;
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

/**
 * @author Braxton Grover, Christian Liechty
 *
 * This class is sets the stage for where the User is able to create, delete, or change Tasks and Lists.
 * Tasks and Lists for the user signed in are displayed here.
 * New lists and tasks are saved to a file, so the User can revisit their ToDoLists.
 */

public class MainUserState extends UIState{

    private static MainUserState singleton;
    private FileManager fileMang= new FileManager();
    ListView<String> listsV = new ListView<String>();
    public static Stage mainUser= new Stage();

    public static MainUserState instance(){
        if (singleton== null){
            singleton = new MainUserState();
        }
        return singleton;
    }

    private MainUserState() {
        //keeps access to the Main system
        super();


        //Lists
        Label lists = new Label("Lists");
        RefreshLists();
        listsV.setMaxSize(450, 800);
        listsV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Task Label
        Label tasks = new Label("Tasks");

        //Instantiate TableView
        TableView tasksV = new TableView();
        tasksV.setPlaceholder(new Label("No tasks to display"));

        //Create Columns for TableView
        TableColumn<Task, String> taskTitle = new TableColumn<>("Title");
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, LocalDate> taskDueDate = new TableColumn<>("Due Date");
        taskDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        tasksV.getColumns().add(taskTitle);
        tasksV.getColumns().add(taskDueDate);



            //Buttons
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

            currentScene = new Scene(hBoxOne, 700, 600);
            mainUser.setScene(currentScene);
            mainUser.show();

            //Button action for Create List
            createList.setOnMouseClicked(event -> {
                createItem();
            });

            //Button action for Create Task
            createTask.setOnMouseClicked(event -> {
                newTask();
            });

            //Button action for Delete Task
            deleteTask.setOnMouseClicked(event -> {
                TableView.TableViewSelectionModel selectionModel = tasksV.getSelectionModel();
                
                sys.getUserList().get(sys.getCurrentUser()).getList(tasksV.getSelectionModel().getSelectedIndex()).removeTask((Task) selectionModel.getSelectedItem());

            });

            //Button action for Logout
            logOut.setOnMouseClicked(event -> {
                mainUser.close();
                sys.logout();
                mainStage.show();
            });

        /**
         * When a list is clicked, the TableView of Tasks is cleared, and then populated with the tasks in that list
         */
        listsV.setOnMouseClicked(event -> {

                tasksV.getItems().clear();
                TaskList currentList=null;
                for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
                    if (sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle().equals(listsV.getSelectionModel().getSelectedItem()) ) {
                        currentList = sys.getUserList().get(sys.getCurrentUser()).getList(i);
                    }
                }


                if (currentList.size()>0) {
                    for (int i = 0; i < currentList.size(); i++) {
                        tasksV.getItems().add(currentList.getTask(i));
                    }
                }

            });
        }
        @Override
        public Stage getStage(){
        return mainUser;
        }

        //going to use this for Lists and create a separate method for tasks

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

                Save();
                RefreshLists();



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

    /**
     * @author Christian Liechty
     * When "Create Task" button is clicked, a new window is displayed where the User can create a new Task.
     * The task is then saved to a file.
     */
    public void newTask() {
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
            tDescription.setMinSize(200, 100);
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

            //Need to add code to add task to a list
            create.setOnMouseClicked(event -> {
                //Use this for getting value from DatePicker
                LocalDate value = dueDate.getValue();

                if (tTitle.getText() != null && tDescription.getText() != null){
                    Task newTask = new Task((Integer) priorityChoice.getValue(), tTitle.getText(),
                            tDescription.getText(), value, tLabel.getText());

                    sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).addTask(newTask);

                } else {
                    this.makePopUp();
                }

                //for saving to the file
                try {
                    fileMang.writeUser("./User.json", sys.getUserList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                taskStage.close();
            });

            //Layout
            VBox first = new VBox(10, title, tTitle, description, tDescription, labelDueDate, dueDate);
            VBox second = new VBox(10, labelPriority, priorityChoice, label, tLabel);
            HBox third = new HBox(10, create, cancel);
            HBox fourth = new HBox(10, first, second);
            VBox fifth = new VBox(10, fourth, third);

            //Scene
            Scene taskScene = new Scene(fifth, 500, 500);

            //Set Stage
            taskStage.setTitle("Create Task");
            taskStage.setScene(taskScene);
            taskStage.show();
        }
        public void RefreshLists(){
            ObservableList<String> listNames = FXCollections.observableArrayList();
            for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
                listNames.add(sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle());
            }
            listsV.setItems(listNames);
        }
        //saves to the file
        public void Save(){
            try {
                fileMang.writeUser("./User.json", sys.getUserList());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

