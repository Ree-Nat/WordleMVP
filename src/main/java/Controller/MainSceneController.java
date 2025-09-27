package Controller;
import Model.LetterStatus;
import View.LetterBox;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {

    @FXML
    public GridPane wordGridPane;
    List<LetterBox> wordList = new ArrayList<>();
    private int currentRow = 1;

    @FXML
    public void initialize() {
        wordGridPane.setFocusTraversable(true); // allow it to be focusable
        wordGridPane.requestFocus();            // request focus so it actually gets key events
    }

    @FXML
    public void listenForInput(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if(keyCode == KeyCode.BACK_SPACE && !wordList.isEmpty() && wordList.size() <= 5)
        {
            removeChild(currentRow-1, wordList.size()-1);
        }

        if (keyCode.isLetterKey() && wordList.size() <=4) {
            Character currentLetter =  keyCode.getChar().charAt(0);
            LetterBox letterBox = new LetterBox(currentLetter, LetterStatus.GREY);
            wordList.add(letterBox);
            populateGrid(currentRow-1, wordList.size()-1, letterBox);
        }
        if(keyCode == KeyCode.ENTER && wordList.size() == 5
        && currentRow < 6) {
            currentRow+=1;
            wordList.clear();
        }

    }

    private void populateGrid(int row, int col, LetterBox letterBox)
    {

        StackPane newTextLetter = letterBox.getLetterBox();
        newTextLetter.getStyleClass().add("letter-box");
        wordGridPane.add(newTextLetter, col, row);
    }

    private void removeChild(int row, int col)
    {
        LetterBox removedChild = wordList.removeLast();
        StackPane removedChild_pane = removedChild.getLetterBox();
        ObservableList<Node> nodeList = wordGridPane.getChildren();
        nodeList.remove(removedChild_pane);
        }

    }

