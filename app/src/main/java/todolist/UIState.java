package todolist;
/**
 * @author Braxton Grover
 */
import javafx.scene.*;
import javafx.stage.*;

public abstract class UIState {

    //The only thing that the
    protected static Stage popupStage;
    protected static Stage mainStage;
    protected static Scene popupScene;
    protected static Scene currentScene;
    protected ToDoListSys sys;

    protected UIState(){
        sys= ToDoListSys.instance();

    }


    //methods
    public void openTask(){}

    public void displayUserInfo(){}

    public void logout(){}

    public void createItem(){}

    public void makePopUp(){}

    public Stage getStage(){
        return mainStage;
    }




}
