module org.example.wordlemvp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens org.example.wordlemvp to javafx.fxml;
    exports org.example.wordlemvp to javafx.fxml;
    exports View to  javafx.graphics;
    //exports Controller to javafx.graphics;;
    opens Controller to javafx.fxml;
}
