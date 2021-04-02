module app.main {
    requires javafx.controls;
    requires javafx.graphics;
    requires com.google.gson;

    exports ToDoList;

    opens ToDoList to com.google.gson;
}