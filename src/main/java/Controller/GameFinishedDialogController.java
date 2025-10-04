package Controller;

import Model.WordleAnswer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameFinishedDialogController {

    @FXML
    public Text wordAnswerText;
    @FXML
    private Text gameStatusText;
    private MainSceneController mainScreenController;
    private Stage dialogStage;
    private boolean winStatus;
    private WordleAnswer answer;


    public void initialize() throws IOException {
    }

    public GameFinishedDialogController() throws IOException {

    }

    //
    public void getFromController(boolean winStatus, WordleAnswer answer, MainSceneController mainSceneController, Stage gameEndStage)
    {
        this.winStatus = winStatus;
        this.answer = answer;
        this.mainScreenController = mainSceneController;
        setStage(gameEndStage);
        dialogStage.setTitle("Game Finished");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        processResult();
    }

    public void processResult()
    {
        if(winStatus){
            gameStatusText.setText("Win");
        }
        else{
            gameStatusText.setText("You Lose");
        }

        wordAnswerText.setText("Wordle Answer was: " + answer.getString());
    }

    public void setStage(Stage stage)
    {
        this.dialogStage = stage;
    }


    public void restartGame(ActionEvent actionEvent) throws IOException {
        mainScreenController.restartScene();
        dialogStage.close();
    }
}
