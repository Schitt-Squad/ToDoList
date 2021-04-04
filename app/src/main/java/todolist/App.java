/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todolist;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    private Stage userMain;
    private Stage userTask;
    private Controller cont;

    private ToDoListSys sys= ToDoListSys.instance();
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Login");

        Label loginLabel= new Label("LOGIN");
        loginLabel.setFont(Font.font(24));

        Label userLabel= new Label("Username:");
        userLabel.setFont(Font.font(16));
        Label passLabel= new Label("Password:");
        passLabel.setFont(Font.font(16));
        TextField userText= new TextField();
        TextField passText= new TextField();

        Button loginButt= new Button();
        loginButt.setText("Login");

        VBox loginOne= new VBox(userLabel, userText, passLabel, passText);
        VBox loginTwo= new VBox(loginLabel, loginOne, loginButt);
        loginOne.setSpacing(10);
        loginTwo.setSpacing(50);
        loginOne.setAlignment(Pos.CENTER);
        loginTwo.setAlignment(Pos.CENTER);
        Scene scene= new Scene(loginTwo, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
