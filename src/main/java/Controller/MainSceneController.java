package Controller;
import Model.*;
import View.LetterBox;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class MainSceneController {

    @FXML
    public GridPane wordGridPane;
    public Text inputMessage;
    public Button saveButton;
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
    private WordleAnswer wordleAnswer;
    private String userInput;
    private HashMap<Character, Button> buttonMapping;
    private final MainSceneController mainSceneController;
    private long randomSeed;



    public MainSceneController() throws IOException {
        this.winStatus = false;
        wordleBoard = new WordleBoard();
        keyboard = new Keyboard();
        wordRepository = new WordList("src/main/resources/Valid_Wordle_Words.json");
        gameModel = new GameModel();
        this.mainSceneController = this;
        long seed= System.currentTimeMillis();
        Random random = new Random(seed);
        randomSeed = random.nextLong();
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
    public void listenForInput(KeyEvent keyEvent) throws IOException {
        KeyCode keyCode = keyEvent.getCode();
        processListener(keyCode);
    }

    @FXML
    public void buttonListener(ActionEvent actionEvent) throws IOException {
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

        processListener(keyCode);
    }


    public void restartScene()
    {
        System.out.println(wordGridPane.getChildren());
        ObservableList<Node> gridNodes = wordGridPane.getChildren();
        // safe removal
        wordGridPane.getChildren().removeIf(item -> item instanceof StackPane);
        for(int i = 0; i < letterBoxList.toArray().length; i++){
            letterBoxList.removeLast();
        }
        reRollWordleWord();
        resetUIKeyboardColor();
        keyboard.resetKeyboardStatus();
        wordleBoard.resetBoard();
        currentRow = 1;
        gameModel.resetGuess();
    }

    public void listenForSaveButton(ActionEvent actionEvent) throws IOException {
        saveGame();
    }


    private void saveGame() throws IOException {
        //create json class
        //get array and populate it with current words in this session
        //output in new json file.

        ArrayList<String> userWordList = wordleBoard.getWordList();
        GsonBuilder gson = new GsonBuilder();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/Savedata/save.json"));
        JsonWriter jsonwriter = gson.create().newJsonWriter(bufferedWriter);
        int place = 0;
        jsonwriter.beginObject();
        for(String word: userWordList)
        {
            jsonwriter.name(Integer.toString(place)).value(word);
            place+=1;

        }
        jsonwriter.endObject();

        jsonwriter.close();
    }

    private void loadGame()
    {

    }

    private void reRollWordleWord()
    {
        Random randomGenerator = new Random();
        Long randomLong = randomGenerator.nextLong();
        String newStringAnswer = wordRepository.getRandomWord(randomLong);
        wordleAnswer = new WordleAnswer(newStringAnswer);
        winStatus = false;

    }

    private void resetUIKeyboardColor()
    {

       for(Button button: buttonMapping.values())
       {
           button.getStyleClass().clear();
           button.getStyleClass().add("button");
           button.getStyleClass().add("buttonClass");
       }
    }

    private void processListener(KeyCode keyCode) throws IOException {
        if(keyCode == KeyCode.BACK_SPACE && !letterBoxList.isEmpty() && letterBoxList.size() <= 5)
        {
            removeChild(currentRow-1, letterBoxList.size()-1);
        }

        if (keyCode.isLetterKey() && letterBoxList.size() <=4 && currentRow <=6) {
            Character currentLetter =  keyCode.getChar().charAt(0);
            LetterBox letterBox = new LetterBox(currentLetter, LetterStatus.GREY);
            letterBoxList.add(letterBox);
            populateGrid(currentRow-1, letterBoxList.size()-1, letterBox);
        }

        if(keyCode == KeyCode.ENTER && /*letterBoxList.size() == 5*/
                 currentRow <= 6) {
            String userInput = getUserInput(letterBoxList);
            userInput = userInput.toLowerCase();
            if(wordRepository.exists(userInput) == true && userInput.length() == 5) {
                inputMessage.setText(""); //reset input message
                winStatus = processInput(userInput);
                wordleBoard.addWord(new UserGuess(userInput));
                currentRow += 1;
                letterBoxList.clear();
                gameModel.increaseGuess();
            }
            else
            {
                processInputMismatch(userInput);
            }
        }
        if(wordleBoard.isFull() || winStatus == true)
        {
            System.out.println("im full");
            switchEndScene(winStatus);
        }
    }


    private void processInputMismatch(String userInput){
        if(userInput.length() < 5)
        {
            inputMessage.setText("String must be exactly 5 characters");
        } else if(!wordRepository.exists(userInput)) {
            inputMessage.setText(userInput + " does not exist in this game!");
        }
    }

    private void switchEndScene(boolean winStatus) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/wordlemvp/GameFinishedDialog.fxml"));
        Parent mainScreen = loader.load();
        Stage secondaryStage = new Stage();
        HBox rootHbox = new HBox();
        rootHbox.getChildren().add(mainScreen);
        HBox.setHgrow(rootHbox, Priority.ALWAYS);
        Scene newScene = new Scene(rootHbox);
        rootHbox.setAlignment(Pos.CENTER);
        secondaryStage.setScene(newScene);
        secondaryStage.setTitle("Game Finished");
        secondaryStage.initModality(Modality.APPLICATION_MODAL);

        GameFinishedDialogController finishedDialogController = loader.getController();


        Text winStatusText = finishedDialogController.getGameStatusText();
        Text correctWordleWord = finishedDialogController.getWordAnswerText();
        finishedDialogController.setMainScreenController(mainSceneController);
        finishedDialogController.setStage(secondaryStage);

        correctWordleWord.setText("Word was " + wordleAnswer.getString());
        if(winStatus == true)
        {
            winStatusText.setText("Win");
        }
        else {winStatusText.setText("Game Over");}


        secondaryStage.show();
    }

    private boolean processInput(String userInput)
    {
        List<LetterBox> outputLetterBox = this.letterBoxList;
        UserGuess newUserGuess = new UserGuess(userInput);
        HashMap<Integer,LetterStatus> color_map = newUserGuess.compare(wordleAnswer);
        keyboard.updateKeyboardStatus(color_map, newUserGuess);
        String processedInput = userInput.toUpperCase();
        winStatus = newUserGuess.equals(wordleAnswer);
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
                currentButton.getStyleClass().clear();
                currentButton.getStyleClass().add("button");
                currentButton.getStyleClass().add("GreenStatus");
            }
            else if(character_status == LetterStatus.YELLOW)
            {
                currentButton.getStyleClass().clear();
                currentButton.getStyleClass().add("button");
                currentButton.getStyleClass().add(("YellowStatus"));
            }
            else if(character_status == LetterStatus.BLACK)
            {
                currentButton.getStyleClass().clear();
                currentButton.getStyleClass().add("button");
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

