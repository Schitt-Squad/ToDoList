package todolist;

/**
 * @author Braxton Grover
 */

import java.util.Calendar;

public class Task implements Cloneable {
    //Instance Variables
    private int priority;
    private String title;
    private String description;
    private String dueDate;
    private String label;
    private boolean completed;

    //Constructor

    /**
     *
     * @param priority      Assigned priority of a given task
     * @param title         Title of task
     * @param description   Description of task to be completed
     * @param dueDate       Date task must be completed by
     * @param label         Allows User to put a label on a task
     */
    public Task(int priority, String title, String description, String dueDate, String label) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.label = label;
        this.completed= false;
    }

    public Task(int priority, String title, String description, String label) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        //this.dueDate = defaultDueDate();
        this.label = label;
        this.completed= false;
    }

    //Empty Constructor
    public Task(){

    }

    //Define getters and setters
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //Define default Due Date for Task

    /**
     *
     * @return Calendar object representing due date for a task
     */
    //public Calendar defaultDueDate(){
    //    Calendar c= Calendar.getInstance();
    //    c.add(Calendar.DATE, 7);
    //    return c;
    //}

    //for duplication
    public Task clone() throws CloneNotSupportedException{
        return (Task)super.clone();
    }

    public void markComplete(){completed=true;}

}
