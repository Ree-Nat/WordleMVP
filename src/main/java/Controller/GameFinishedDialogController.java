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

    /**
     * Gets necessary variables from main scene once main game ends
     * @param winStatus a boolean
     * @param answer a WordleWord object
     * @param mainSceneController a MainSceneController object
     * @param gameEndStage a GameEndStage object
     */
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

    /**
     * Process end game result from controller's variable
     */
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

    /**
     * Sets the stage of the controller
     * @param stage
     */
    public void setStage(Stage stage)
    {
        this.dialogStage = stage;
    }


    /**
     * Restarts the game from the main controller class
     * @param actionEvent an ActionEvent object
     * @throws IOException if main scene does not exist
     */
    public void restartGame(ActionEvent actionEvent) throws IOException {
        mainScreenController.restartScene();
        dialogStage.close();
    }
}
