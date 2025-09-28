package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXWordleGame extends Application {


    HBox hbox = new HBox();

    public void start(Stage primaryStage) throws Exception {

        try {
            //load windows
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/wordlemvp/MainScreen.fxml"));
            Parent mainScreen = loader.load();
            hbox.getChildren().add(mainScreen);
            HBox.setHgrow(hbox, Priority.ALWAYS);
            Scene newScene = new Scene(hbox);
            hbox.setAlignment(Pos.CENTER);
            primaryStage.setScene(newScene);
            newScene.getStylesheets().add(getClass().getResource("/org/example/wordlemvp/MainScreen.css").toExternalForm());
            primaryStage.setWidth(1920);
            primaryStage.setHeight(1080);
            hbox.setBackground(Background.fill(Color.web("#292929")));
            primaryStage.setTitle("Wordle MVP");
            //create game object.
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
