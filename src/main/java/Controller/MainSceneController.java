package Controller;
import Model.*;
import View.LetterBox;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javax.print.DocFlavor;
import java.io.IOException;
import java.util.*;

public class MainSceneController {

    @FXML
    public GridPane wordGridPane;
    @FXML
    private VBox keyboardBox;

    List<LetterBox> letterBoxList = new ArrayList<>();
    private int currentRow = 1;
    private WordleWord currentUserInput;
    private final Keyboard keyboard;
    private final WordleBoard wordleBoard;
    private final GameModel gameModel;
    private final WordList wordRepository; //letterBoxList;
    private Boolean winStatus;
    private final WordleAnswer wordleAnswer;
    private String userInput;
    private HashMap<Character, Button> buttonMapping;



    public MainSceneController() throws IOException {
        this.winStatus = false;
        wordleBoard = new WordleBoard();
        keyboard = new Keyboard();
        wordRepository = new WordList("src/main/resources/Valid_Wordle_Words.json");
        gameModel = new GameModel();
        long seed= System.currentTimeMillis();
        Random random = new Random(seed);
        long randomSeed = random.nextLong();
        String randomWord = wordRepository.getRandomWord(randomSeed);
        this.wordleAnswer = new WordleAnswer(randomWord);
    }

    @FXML
    public void initialize(){
        wordGridPane.setFocusTraversable(true);
        wordGridPane.requestFocus();
        this.buttonMapping = this.getButtonMapping();
    }

    @FXML
    public void listenForInput(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if(keyCode == KeyCode.BACK_SPACE && !letterBoxList.isEmpty() && letterBoxList.size() <= 5)
        {
            removeChild(currentRow-1, letterBoxList.size()-1);
        }

        if (keyCode.isLetterKey() && letterBoxList.size() <=4) {
            Character currentLetter =  keyCode.getChar().charAt(0);
            LetterBox letterBox = new LetterBox(currentLetter, LetterStatus.GREY);
            letterBoxList.add(letterBox);
            populateGrid(currentRow-1, letterBoxList.size()-1, letterBox);
        }
        if(keyCode == KeyCode.ENTER && letterBoxList.size() == 5
        && currentRow <= 6) {
            String userInput = getUserInput(letterBoxList);
            userInput = userInput.toLowerCase();
            if(wordRepository.exists(userInput) == true) {
                winStatus = processInput(userInput);
                currentRow += 1;
                letterBoxList.clear();
            }
        }
    }

    @FXML
    public void buttonListener(ActionEvent actionEvent) {
        Object buttonSource = actionEvent.getSource();
        Button button = (Button) buttonSource;
        String buttonText = button.getText();
        KeyCode keyCode;
        if(buttonText.equals("Enter"))
        {
            keyCode =KeyCode.valueOf("ENTER");
        } else if (buttonText.equals("BCK")) {
            keyCode = KeyCode.valueOf("BACK_SPACE");
        }
        else{keyCode = KeyCode.valueOf(button.getText());}


        if(keyCode == KeyCode.BACK_SPACE && !letterBoxList.isEmpty() && letterBoxList.size() <= 5)
        {
            removeChild(currentRow-1, letterBoxList.size()-1);
        }

        if (keyCode.isLetterKey() && letterBoxList.size() <=4) {
            Character currentLetter =  keyCode.getChar().charAt(0);
            LetterBox letterBox = new LetterBox(currentLetter, LetterStatus.GREY);
            letterBoxList.add(letterBox);
            populateGrid(currentRow-1, letterBoxList.size()-1, letterBox);
        }
        if(keyCode == KeyCode.ENTER && letterBoxList.size() == 5
                && currentRow <= 6) {
            String userInput = getUserInput(letterBoxList);
            userInput = userInput.toLowerCase();
            if(wordRepository.exists(userInput) == true) {
                winStatus = processInput(userInput);
                currentRow += 1;
                letterBoxList.clear();
            }
        }
    }

    private boolean processInput(String userInput)
    {
        List<LetterBox> outputLetterBox = this.letterBoxList;
        UserGuess newUserGuess = new UserGuess(userInput);
        HashMap<Integer,LetterStatus> color_map = newUserGuess.compare(wordleAnswer);
        keyboard.updateKeyboardStatus(color_map, newUserGuess);
        String processedInput = userInput.toUpperCase();
        winStatus = checkWin(color_map);
        int index = 1;
        for(LetterBox LetterBoxNode : letterBoxList)
        {
            Character currentCharacter = processedInput.charAt(index-1);
            LetterBox newLetterBox = new LetterBox(currentCharacter, color_map.get(index-1));
            int removedPlace = wordGridPane.getChildren().indexOf(LetterBoxNode.getLetterBoxContainer());
            wordGridPane.getChildren().remove(LetterBoxNode.getLetterBoxContainer());
            wordGridPane.add(newLetterBox.getLetterBoxContainer(), index-1, currentRow-1);
            index+=1;
        }
        recolorKeyboard();
        return winStatus;
    }

    private void recolorKeyboard()
    {
        HashMap<Character, LetterStatus> keyboardMap = keyboard.getKeyMap();
        Set<Character> characterSet = keyboardMap.keySet();
        for(Character character: characterSet)
        {
            LetterStatus character_status = keyboardMap.get(character);
            Button currentButton = buttonMapping.get(character);
            currentButton.getStyleClass().removeAll();
            if(character_status == LetterStatus.GREEN)
            {
                currentButton.getStyleClass().add("GreenStatus");
            }
            else if(character_status == LetterStatus.YELLOW)
            {
                currentButton.getStyleClass().add(("YellowStatus"));
            }
            else if(character_status == LetterStatus.BLACK)
            {
                currentButton.getStyleClass().add("BlackStatus");
            }

        }
    }

    private boolean checkWin(HashMap<Integer, LetterStatus> output)
    {
        LetterStatus[] colors = LetterStatus.values();
        List<LetterStatus> letterList = new ArrayList<>(Arrays.asList(colors));
        return !letterList.contains(LetterStatus.YELLOW) && !letterList.contains(LetterStatus.BLACK);
    }


    public void checkGameStatus()
    {

    }


    private String getUserInput(List<LetterBox> letterBoxList)
    {
        StringBuilder userInput = new StringBuilder();
        for(LetterBox LetterBoxNode : letterBoxList)
        {
            Character currentCharacter = LetterBoxNode.getLetterBoxCharacter();
            userInput.append(currentCharacter);
        }
        return userInput.toString();
    }

    private void populateGrid(int row, int col, LetterBox letterBox)
    {
        StackPane newTextLetter = letterBox.getLetterBoxContainer();
        newTextLetter.getStyleClass().add("letter-box");
        wordGridPane.add(newTextLetter, col, row);
    }

    private void removeChild(int row, int col)
    {
        LetterBox removedChild = letterBoxList.removeLast();
        StackPane removedChild_pane = removedChild.getLetterBoxContainer();
        ObservableList<Node> nodeList = wordGridPane.getChildren();
        nodeList.remove(removedChild_pane);
        }

    /**
     * Gets button references of UI to make it easier to change color of keyboard
     */
    private HashMap<Character, Button> getButtonMapping()
    {
        int rows = 3;
        HashMap<Character, Button> buttonReferenceMap = new HashMap<>();
        ObservableList<Node> nodeItems = keyboardBox.getChildren();
        for(int x = 0; x < rows; x++)
        {
            HBox HBoxItems = (HBox) nodeItems.get(x);
            ObservableList<Node> buttonRowList = HBoxItems.getChildren();
            for(int y = 0; y < buttonRowList.size(); y++)
            {
                Object buttonObject = buttonRowList.get(y);
                Button button = (Button) buttonObject;
                String buttonText = button.getText();
                if(buttonText.length() == 1)
                {
                    Character buttonChar = buttonText.charAt(0);
                    buttonReferenceMap.put(buttonChar, button);
                }
            }
        }
        return buttonReferenceMap;
    }

}

