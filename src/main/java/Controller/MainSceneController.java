package Controller;
import Model.LetterStatus;
import View.LetterBox;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {

    @FXML
    public GridPane wordGridPane;
    List<Character> wordList = new ArrayList<>();
    private int currentRow = 1;

    @FXML
    public void initialize() {
        wordGridPane.setFocusTraversable(true); // allow it to be focusable
        wordGridPane.requestFocus();            // request focus so it actually gets key events
    }

    @FXML
    public void listenForInput(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.isLetterKey() && wordList.size() <=4) {
            System.out.println(keyEvent.getCode());
            wordList.add(keyCode.getChar().charAt(0));
            populateGrid(currentRow-1, wordList.size()-1, keyCode.getChar().charAt(0));
            System.out.println(wordList);
        }
        else if(keyCode == KeyCode.ENTER && wordList.size() >= 5
        && currentRow < 6) {
            currentRow+=1;
            wordList.clear();
        }
    }

    private void populateGrid(int row, int col, Character letter)
    {
        LetterBox letterBox = new LetterBox(letter, LetterStatus.GREY);
        Text newTextLetter = letterBox.getLetterBox();
        wordGridPane.add(newTextLetter, col, row);
    }
}
