package ToDoList;

public class List {
    //local variables, need to add an array list of tasks
    private String Title;
    private String Description;

    //for both being set
    public List(String title, String desc){
        Title=title;
        Description=desc;
    }

    //when they just want a title to the list
    public List(String title){
        Title=title;
    }

    //creates a blank list
    public List(){
        Title= " ";
        Description= " ";
    }

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
    public void makeNewTask(){

    }

    //makes a duplicate list with all the tasks and description of the desired list
    public void duplicateList(){

    }

    //this will bring up the list of tasks marked complete
    public void showCompletedTasks(){

    }

    //moves a task from one list to another
    public void moveTask(){

    }
}
