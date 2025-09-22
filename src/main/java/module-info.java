module org.example.wordlemvp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.wordlemvp to javafx.fxml;
    exports org.example.wordlemvp;
}