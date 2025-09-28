package Controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GameFinishedDialogController {

    @FXML
    public Text wordAnswerText;
    @FXML
    Text gameStatusText;


    public void setWordAnswerText(Text wordAnswerText) {
        this.wordAnswerText = wordAnswerText;
    }

    public void setGameStatusText(Text wordAnswerText) {
        this.gameStatusText = wordAnswerText;
    }

    public Text getWordAnswerText()
    {
        return wordAnswerText;
    }

    public Text getGameStatusText()
    {
        return gameStatusText;
    }



}
