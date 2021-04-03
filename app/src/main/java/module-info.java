module ToDoList.app.main {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.graphics;

    exports todolist to javafx.graphics;
}