package todolist;

/**
 * This class handles the ArrayList of Tasks that have been created by Users.
 * Tasks can be added to the list, duplicated, or moved to different lists.
 *
 * @author Braxton Grover
 */

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskList implements Cloneable, Serializable {
    //local variables, need to add an array list of tasks
    private String Title;
    private String Description;
    private ArrayList<Task> taskList= new ArrayList<Task>();

    //for both being set
    public TaskList(String title, String desc){
        if (title != null && desc !=null) {
            Title = title;
            Description = desc;
        }else if (desc == null && title != null) {
            Title=title;
            Description=" ";
        }else {
            Title= "Blank";
            Description= " ";
        }
    }


    //Getters and Setters
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    //need this method for cloning
    public void cloneTask(Task task){
        taskList.add(task);
    }

    //moves a task from one list to another
    public void moveTask(TaskList list, Task task){
        list.addTask(task);
        this.removeTask(task);
    }

    //for duplication
    public TaskList clone() throws CloneNotSupportedException {
        TaskList clone= (TaskList)super.clone();
        clone.setTitle(clone.getTitle()+ " (Copy)");
        return clone;
    }

    //for moving tasks
    public void addTask(Task task){
        taskList.add(task);
    }
    public void removeTask(Task task){
        taskList.remove(task);
    }
    public int size() {return taskList.size();}
    public Task getTask(int index) {return taskList.get(index);}
}
