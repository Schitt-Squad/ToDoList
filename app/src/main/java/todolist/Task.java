package todolist;

/**
 * @author Braxton Grover
 */

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Task implements Cloneable {
    //Instance Variables
    private int priority;
    private String title;
    private String description;
    private LocalDate dueDate;
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
    public Task(int priority, String title, String description, LocalDate dueDate, String label) {
        if (title != null && description != null && dueDate !=null && label != null) {
            this.priority = priority;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.label = label;
        } else if (title != null && description != null && dueDate ==null && label != null){
            this.priority = priority;
            this.title = title;
            this.description = description;
            this.dueDate = LocalDate.now().plusDays(7);
            this.label = label;
        } else {
            this.priority=1;
            this.title = "empty";
            this.description= "nada";
            this.label= "";
            this.dueDate= LocalDate.now().plusWeeks(1);
        }

        this.completed= false;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //for duplication
    public Task clone() throws CloneNotSupportedException{
        Task clone= (Task)super.clone();
        clone.setTitle(clone.getTitle()+" (Copy)");
        return clone;
    }

    public void markComplete(){completed=true;}

    public boolean isCompleted(){
        return completed;
    }

}
