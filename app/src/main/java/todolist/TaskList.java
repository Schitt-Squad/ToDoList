package todolist;

/**
 * @author Braxton Grover
 */

import java.util.ArrayList;
import java.util.Calendar;

public class TaskList implements Cloneable{
    //local variables, need to add an array list of tasks
    private String Title;
    private String Description;
    private ArrayList<Task> taskList= new ArrayList<Task>();

    //for both being set
    public TaskList(String title, String desc){
        Title=title;
        Description=desc;
    }

    //when they just want a title to the list
    public TaskList(String title){
        Title=title;
    }

    //creates a blank list
    public TaskList(){
        Title= " ";
        Description= " ";
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

    //make a new task and add it to the list
    public void makeNewTask(int priority, String title, String desc, Calendar date, String label){
        Task t;
        if (date== null){
            t= new Task(priority, title, desc, label);
        } else {
            t= new Task(priority, title, desc, date, label);
        }
        taskList.add(t);
    }

    //moves a task from one list to another
    public void moveTask(TaskList list, Task task){
        list.addTask(task);
        this.removeTask(task);
    }

    //Allows user to duplicate a task
    //Return type and parameters need to be changed
    public void duplicateTask(Task orig) throws CloneNotSupportedException{
        Task newTask= orig.clone();
        newTask.setLabel(newTask.getTitle() + " Copy");
        taskList.add(newTask);
    }

    //for duplication
    public TaskList clone() throws CloneNotSupportedException {
        return (TaskList)super.clone();
    }

    //for moving tasks
    public void addTask(Task task){
        taskList.add(task);
    }
    public void removeTask(Task task){
        taskList.remove(task);
    }
}
