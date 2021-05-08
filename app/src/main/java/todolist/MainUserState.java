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
    //Instance Variables
    private static MainUserState singleton;
    private FileManager fileMang= new FileManager();
    ListView<String> listsV = new ListView<String>();
    TableView tasksV = new TableView();
    public static Stage mainUser= new Stage();
    private CheckBox completedTasks;

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
        //TableView tasksV = new TableView();
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
            Button duplicateList= new Button("Duplicate List");
            Button renameList= new Button("Rename List");
            Button editList= new Button("View/Edit Description");
            Button createTask = new Button("Create Task");
            Button deleteTask = new Button("Delete Task");
            Button duplicateTask= new Button("Duplicate Task");
            Button moveTask = new Button("Move Task");
            Button viewTask = new Button("ViewTask");
            Button logOut = new Button("Logout");
            Button userProfile = new Button("View User Profile");

            completedTasks= new CheckBox("Show Completed Tasks");

            //Alignment
            VBox vBoxOne = new VBox(lists, listsV, logOut, userProfile);
            vBoxOne.setAlignment(Pos.CENTER_LEFT);
            vBoxOne.setSpacing(20);
            VBox vBoxListButtons= new VBox(createList, deleteList, renameList, editList, duplicateList);
            vBoxListButtons.setAlignment(Pos.CENTER);
            vBoxListButtons.setSpacing(20);
            VBox vBoxTwo = new VBox(completedTasks, tasks, tasksV);
            vBoxTwo.setAlignment(Pos.CENTER_LEFT);
            vBoxTwo.setSpacing(10);
            VBox vBoxTasksButtons = new VBox(createTask, deleteTask, duplicateTask, moveTask, viewTask);
            vBoxTasksButtons.setAlignment(Pos.CENTER);
            vBoxTasksButtons.setSpacing(20);
            HBox hBoxOne = new HBox(vBoxOne, vBoxListButtons, vBoxTwo, vBoxTasksButtons);
            hBoxOne.setSpacing(50);
            hBoxOne.setAlignment(Pos.BOTTOM_CENTER);

            //Set the Scene
            currentScene = new Scene(hBoxOne, 850, 600);
            mainUser.setTitle("Welcome, " + sys.getUserList().get(sys.getCurrentUser()).getFirstName());
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

            editList.setOnMouseClicked(event -> {
                EditList();
            });

            //Button action for Delete Task
            deleteTask.setOnMouseClicked(event -> {
                Task remove = sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).getTask(tasksV.getSelectionModel().getSelectedIndex());

                sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).removeTask(remove);

                Save();
                RefreshTasks();
            });

            //Button action for Delete List
            deleteList.setOnMouseClicked(event -> {
                TaskList delete = sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex());
                sys.getUserList().get(sys.getCurrentUser()).removeList(delete);

                Save();
                RefreshLists();
            });

            //Button action for viewing task
            viewTask.setOnMouseClicked(event -> {
                view();

                Save();

            });

            //Button action for Logout
            logOut.setOnMouseClicked(event -> {
                mainUser.close();
                sys.logout();
                mainStage.show();
            });

            //Button action for View Profile
            userProfile.setOnMouseClicked(event -> {
                viewUser();
            });

            //Button action for duplicate list
            duplicateList.setOnMouseClicked(event -> {
                TaskList orig= sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex());
                try {
                    sys.getUserList().get(sys.getCurrentUser()).newList(orig.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                RefreshLists();
                Save();
            });

            //Button action for duplicate task
            duplicateTask.setOnMouseClicked(event -> {
                Task orig = sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).getTask(tasksV.getSelectionModel().getSelectedIndex());
                try {
                    sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).cloneTask(orig.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                RefreshTasks();
            });

            completedTasks.setOnMouseClicked(event -> {
                RefreshTasks();
            });

        /**
         * When a list is clicked, the TableView of Tasks is cleared, and then populated with the tasks in that list
         */
        listsV.setOnMouseClicked(event -> {
            RefreshTasks();
            });

        //Button action for renaming lists
         renameList.setOnMouseClicked(event -> {
             RenameList();
         });
        }

        @Override
        public Stage getStage(){
        return mainUser;
        }

        //Method that sets the stage for creating a new list
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

        //Method informing user if Item was created successfully
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
            TextArea tDescription = new TextArea();
            tDescription.setWrapText(true);
            tDescription.setMaxSize(200, 200);
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
            priorityChoice.setValue(1);

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

            //Button action for creating Task
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
                Save();
                RefreshTasks();

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

        private void EditList(){
        Stage list= new Stage();
        TaskList currentList= sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex());
        Label title= new Label("Title: \n"+ currentList.getTitle());
        Label desc= new Label("Description:");
        TextArea descT= new TextArea();
        descT.setText(currentList.getDescription());
        descT.setWrapText(true);
        descT.setMaxSize(200, 100);
        Button save= new Button("Save");
        Button cancel= new Button("Cancel");

        VBox descL= new VBox(desc, descT);
        descL.setAlignment(Pos.CENTER_LEFT);
        HBox buttons= new HBox(save, cancel);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setSpacing(20);
        VBox layout= new VBox(title, descL, buttons);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(30);
        Scene scene = new Scene(layout, 300, 400);
        list.setScene(scene);
        list.show();

        cancel.setOnMouseClicked(event -> {list.close();});

        save.setOnMouseClicked(event -> {
            sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).setDescription(descT.getText());
            Save();
            RefreshLists();
            list.close();
        });

        }

        private void RenameList(){
        Stage rename= new Stage();
        rename.setTitle("New Name");

        Label newName= new Label("New Name");
        TextArea newTitle= new TextArea();
        newTitle.setWrapText(true);
        newTitle.setMaxSize(100, 50);
        Button cancel= new Button("Cancel");
        Button save = new Button("Save");

        HBox buttons= new HBox(save, cancel);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setSpacing(30);
        VBox layout= new VBox(newName, newTitle, buttons);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setSpacing(20);

        Scene scene= new Scene(layout, 300, 200);
        rename.setScene(scene);
        rename.show();

        cancel.setOnMouseClicked(event -> {
            rename.close();
        });
        save.setOnMouseClicked(event -> {
            sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).setTitle(newTitle.getText());
            RefreshLists();
            Save();
            rename.close();
        });

        }

        //Method for refreshing lists. That way User does not have to click on list again to refresh.
        public void RefreshLists(){
            ObservableList<String> listNames = FXCollections.observableArrayList();
            for (int i = 0; i < sys.getUserList().get(sys.getCurrentUser()).getListArraySize(); i++) {
                listNames.add(sys.getUserList().get(sys.getCurrentUser()).getList(i).getTitle());
            }
            listsV.setItems(listNames);
        }

        //Method for refreshing tasks. That way User does not have to click on list again to refresh.
        public void RefreshTasks(){
            tasksV.getItems().clear();
            TaskList currentList=sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex());
            if (completedTasks.isSelected()) {
                if (currentList.size() > 0) {
                    for (int i = 0; i < currentList.size(); i++) {
                            tasksV.getItems().add(currentList.getTask(i));

                    }
                }
            } else {
                if (currentList.size() > 0) {
                    for (int i = 0; i < currentList.size(); i++) {
                        if (currentList.getTask(i).isCompleted()== false) {
                            tasksV.getItems().add(currentList.getTask(i));
                        }
                    }
                }
            }

        }

        //saves to the file
        public void Save(){
            try {
                fileMang.writeUser("./User.json", sys.getUserList());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //This method creates a scene from which the User can view and edit tasks that have already been created.
        public void view(){
            Stage viewStage = new Stage();

            Task viewed = sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).getTask(tasksV.getSelectionModel().getSelectedIndex());

            //Labels
            Label title = new Label("Title");
            Label description = new Label("Description");
            Label labelDueDate = new Label("Due Date");
            Label labelPriority = new Label("Priority");
            Label label = new Label("Label");

            //Textfields
            TextField tTitle = new TextField(viewed.getTitle());
            TextArea tDescription = new TextArea(viewed.getDescription());
            tDescription.setWrapText(true);
            tDescription.setMaxSize(200, 200);
            TextField tLabel = new TextField(viewed.getLabel());

            //DatePicker
            DatePicker dueDate = new DatePicker(viewed.getDueDate());

            //ChoiceBox for selecting priority of tasks
            ChoiceBox priorityChoice = new ChoiceBox();
            priorityChoice.setValue(viewed.getPriority());

            //Add options to ChoiceBox
            priorityChoice.getItems().add(1);
            priorityChoice.getItems().add(2);
            priorityChoice.getItems().add(3);
            priorityChoice.getItems().add(4);
            priorityChoice.getItems().add(5);


            //Buttons
            Button change = new Button("Change Task");
            Button cancel = new Button("Cancel");

            //Button Actions
            /**
             * When the "Cancel" button is clicked the stage will close
             * and a new task will not be created
             */
            cancel.setOnMouseClicked(event -> {
                viewStage.close();
            });

            //Button action for changing Task
            change.setOnMouseClicked(event -> {
                //Use this for getting value from DatePicker
                LocalDate value = dueDate.getValue();

                if (tTitle.getText() != null && tDescription.getText() != null){
                    Task newTask = new Task((Integer) priorityChoice.getValue(), tTitle.getText(),
                            tDescription.getText(), value, tLabel.getText());

                    sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).removeTask(viewed);

                    sys.getUserList().get(sys.getCurrentUser()).getList(listsV.getSelectionModel().getSelectedIndex()).addTask(newTask);

                } else {
                    this.makePopUp();
                }
                Save();
                RefreshLists();

                viewStage.close();
            });

            //Layout
            VBox first = new VBox(10, title, tTitle, description, tDescription, labelDueDate, dueDate);
            VBox second = new VBox(10, labelPriority, priorityChoice, label, tLabel);
            HBox third = new HBox(10, change, cancel);
            HBox fourth = new HBox(10, first, second);
            VBox fifth = new VBox(10, fourth, third);

            //Scene
            Scene taskScene = new Scene(fifth, 500, 500);

            //Set Stage
            viewStage.setTitle("View Task");
            viewStage.setScene(taskScene);
            viewStage.show();

        }

        public void viewUser(){
            //Set stage
            Stage viewUser = new Stage();

            //Labels
            Label username = new Label("Username:");
            Label password = new Label("Password:");
            Label email = new Label("Email: ");
            Label fName = new Label("First Name: ");
            Label lName = new Label("Last Name: ");
            Label bio = new Label("Biography: ");

            User viewed = sys.getUserList().get(sys.getCurrentUser());

            //TextFields
            TextField userT = new TextField(viewed.getUserName());
            userT.setEditable(false);
            userT.setMaxSize(150, 60);

            TextField passT = new TextField(viewed.getPassword());
            passT.setEditable(false);
            passT.setMaxSize(150, 60);

            TextField mailT = new TextField(viewed.getEmailAddress());
            mailT.setEditable(false);
            mailT.setMaxSize(150, 60);

            TextField fNameT = new TextField(viewed.getFirstName());
            fNameT.setEditable(false);
            fNameT.setMaxSize(150, 60);

            TextField lNameT = new TextField(viewed.getLastName());
            lNameT.setEditable(false);
            lNameT.setMaxSize(150, 60);

            TextArea bioT = new TextArea(viewed.getShortBio());
            bioT.setEditable(false);
            bioT.setWrapText(true);
            bioT.setMaxSize(300, 200);

            //Buttons
            Button close = new Button("Close");

            close.setOnMouseClicked(event -> {
                viewUser.close();
            });

            //Alignment
            HBox hBox= new HBox(close);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.setSpacing(20);
            VBox newUserBox= new VBox(fName, fNameT, lName, lNameT, username, userT, password, passT, email, mailT, bio, bioT, hBox);
            newUserBox.setAlignment(Pos.CENTER_LEFT);
            newUserBox.setSpacing(10);
            Scene newUserScene= new Scene(newUserBox, 500, 600);

            viewUser.setTitle("View Profile");
            viewUser.setScene(newUserScene);
            viewUser.show();
        }


    }

