package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXWordleGame extends Application {


    Group rootNode = new Group();

    public void start(Stage primaryStage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/wordlemvp/MainScreen.fxml"));
            Parent mainScreen = loader.load();
            Scene newScene = new Scene(mainScreen);
            primaryStage.setScene(newScene);
            newScene.getStylesheets().add(getClass().getResource("/org/example/wordlemvp/MainScreen.css").toExternalForm());

            primaryStage.setWidth(1920);
            primaryStage.setHeight(1080);
            primaryStage.setTitle("Wordle MVP");

            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
