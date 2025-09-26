package View;

import Controller.MainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FXWordleGame extends Application {


    public void start(Stage primaryStage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/wordlemvp/MainScreen.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            primaryStage.setScene(newScene);
            newScene.getStylesheets().add(getClass().getResource("/org/example/wordlemvp/LetterBoxStyle.css").toExternalForm());
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
