package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameFinishedDialogController {

    @FXML
    public Text wordAnswerText;
    @FXML
    private Text gameStatusText;
    private MainSceneController mainScreenController;
    private Stage dialogStage;

    public void initialize() throws IOException {


    }

    public GameFinishedDialogController() throws IOException {

    }


    public void setStage(Stage stage)
    {
        this.dialogStage = stage;
    }

    public void setWordAnswerText(Text wordAnswerText) {
        this.wordAnswerText = wordAnswerText;
    }

    public void setGameStatusText(Text wordAnswerText) {
        this.gameStatusText = wordAnswerText;
    }

    public void setMainScreenController(MainSceneController mainScreenController)
    {
        this.mainScreenController = mainScreenController;
    }

    public Text getWordAnswerText()
    {
        return wordAnswerText;
    }

    public Text getGameStatusText()
    {
        return gameStatusText;
    }


    public void restartGame(ActionEvent actionEvent) throws IOException {
        mainScreenController.restartScene();
        dialogStage.close();
    }
}
