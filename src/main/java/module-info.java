module org.example.wordlemvp
{
    requires com.google.gson;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens View to  javafx.fxml;
    opens Controller to javafx.fxml;
    exports org.example.wordlemvp to javafx.controls, javafx.fxml;
    exports Controller to javafx.controls, javafx.fxml, com.google.gson;
    exports View to javafx.graphics;
    exports Model to com.google.gson;
}