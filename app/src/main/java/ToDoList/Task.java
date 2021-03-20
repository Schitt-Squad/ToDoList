package ToDoList;

import java.util.Calendar;

public class Task {
    //Instance Variables
    private int priority;
    private String title;
    private String description;
    private Calendar dueDate;
    private String label;

    //Constructor
    public Task(int priority, String title, String description, Calendar dueDate, String label) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.label = label;
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

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //Define default Due Date for Task
    public Calendar defaultDueDate(){
        return null;
    }

    //Allows user to duplicate a task
    //Return type and parameters need to be changed
    public boolean duplicateTask(){
        return true;
    }

}
