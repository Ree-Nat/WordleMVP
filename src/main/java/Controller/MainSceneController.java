package Controller;
import Model.*;
import View.LetterBox;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.List;

public class MainSceneController {

    @FXML
    public GridPane wordGridPane;
    public Text inputMessage;
    public Button saveButton;
    @FXML
    private VBox keyboardBox;

    List<LetterBox> inputBuffer = new ArrayList<>();
    private final Keyboard keyboard;
    private final WordleBoard wordleBoard;
    private final GameModel gameModel;
    private final WordList wordRepository; //letterBoxList;
    private Boolean winStatus;
    private WordleAnswer wordleAnswer;
    private HashMap<Character, Button> buttonMapping;
    private final MainSceneController mainSceneController;


    public MainSceneController() throws IOException {
        this.winStatus = false;
        wordleBoard = new WordleBoard();
        keyboard = new Keyboard();
        wordRepository = new WordList("src/main/resources/Valid_Wordle_Words.json");
        gameModel = new GameModel();
        this.mainSceneController = this;
        this.wordleAnswer = new WordleAnswer(wordRepository.getRandomWord());
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
        // safe removal
        wordGridPane.getChildren().removeIf(item -> item instanceof StackPane);
        for(int i = 0; i < inputBuffer.toArray().length; i++){
            inputBuffer.removeLast();
        }
        reRollWordleWord();
        resetUIKeyboardColor();
        keyboard.resetKeyboardStatus();
        wordleBoard.resetBoard();
        gameModel.resetGuess();
    }

    public void listenForSaveButton(ActionEvent actionEvent) throws IOException {
        inputMessage.setText("Game saved");
        saveGame();
    }

    public void loadGameListener(ActionEvent actionEvent) throws FileNotFoundException {
        inputMessage.setText("Loaded game");
        loadGame();
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
        jsonwriter.name("WordleAnswer").value(this.wordleAnswer.getString());
        for(String word: userWordList)
        {
            jsonwriter.name(Integer.toString(place)).value(word);
            place+=1;

        }
        jsonwriter.endObject();

        jsonwriter.close();
    }

    private void loadGame() throws FileNotFoundException {
        restartScene();
        resetUIKeyboardColor();
        wordleBoard.clearBoard();
        inputBuffer.clear();

        String filePath = "src/main/resources/Savedata/save.json";
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object jsonFile = gson.fromJson(br, Object.class);

        String result = jsonFile.toString();
        JsonElement element = JsonParser.parseString(result);
        JsonObject obj = element.getAsJsonObject();
        List<String> wordList = new ArrayList<>();
        String wordleAnswer_string = obj.get("WordleAnswer").getAsString();
        this.wordleAnswer =  new WordleAnswer(wordleAnswer_string);
        for(int size = 0; size < obj.size()-1; size+=1)
        {
            wordList.add(obj.get(Integer.toString(size)).getAsString());
        }

        for(String word: wordList)
        {
            for(Character currentCharacter: word.toCharArray())
            {

                LetterBox newLetterBox = new LetterBox(currentCharacter, LetterStatus.GREY);
                inputBuffer.add(newLetterBox);
            }
            processInput(word);
            wordleBoard.addWord(new UserGuess(word));
            gameModel.increaseGuess();
            inputBuffer.clear();
        }
    }

    private void reRollWordleWord()
    {
        wordleAnswer = new WordleAnswer(wordRepository.getRandomWord());
        winStatus = false;
    }

    private void resetUIKeyboardColor()
    {

       for(Button button: buttonMapping.values())
       {
           button.getStyleClass().clear();
           button.getStyleClass().add("button");
       }
    }

    private void processListener(KeyCode keyCode) throws IOException {
        if(keyCode == KeyCode.BACK_SPACE && !inputBuffer.isEmpty() && inputBuffer.size() <= 5)
        {
            removeLastGridChild();
        }

        if (keyCode.isLetterKey() && inputBuffer.size() <=4 && wordleBoard.getCurrentSize() <=6) {
            Character currentLetter =  keyCode.getChar().charAt(0);
            LetterBox letterBox = new LetterBox(currentLetter, LetterStatus.GREY);
            inputBuffer.add(letterBox);
            populateGrid(wordleBoard.getCurrentSize(), inputBuffer.size()-1, letterBox);
        }

        if(keyCode == KeyCode.ENTER && /*inputBuffer.size() == 5*/
                wordleBoard.getCurrentSize() <= 6) {
            String userInput = getUserInput(inputBuffer);
            if(wordRepository.exists(userInput) == true && userInput.length() == 5) {
                inputMessage.setText(""); //reset input message
                winStatus = processInput(userInput);
                wordleBoard.addWord(new UserGuess(userInput));
                inputBuffer.clear();
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
        UserGuess newUserGuess = new UserGuess(userInput);
        HashMap<Integer,LetterStatus> color_map = newUserGuess.getColorMap(wordleAnswer);
        keyboard.updateKeyboardStatus(color_map, newUserGuess);
        winStatus = newUserGuess.equals(wordleAnswer);
        int index = 1;
        for(LetterBox LetterBoxNode : inputBuffer)
        {
            Character currentCharacter = userInput.charAt(index-1);
            LetterBox newLetterBox = new LetterBox(currentCharacter, color_map.get(index-1));
            wordGridPane.getChildren().remove(LetterBoxNode.getLetterBoxContainer());
            wordGridPane.add(newLetterBox.getLetterBoxContainer(), index-1, wordleBoard.getCurrentSize());
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
            currentButton.getStyleClass().removeAll("GreenStatus", "YellowStatus", "BlackStatus");
            if(character_status == LetterStatus.GREEN)
            {

                //currentButton.getStyleClass().add("button");
                currentButton.getStyleClass().add("GreenStatus");
            }
            else if(character_status == LetterStatus.YELLOW)
            {
                //currentButton.getStyleClass().add("button");
                currentButton.getStyleClass().add("YellowStatus");
            }
            else if(character_status == LetterStatus.BLACK)
            {
                //currentButton.getStyleClass().add("button");
                currentButton.getStyleClass().add("BlackStatus");
            }

        }
    }



    private String getUserInput(List<LetterBox> inputBuffer)
    {
        StringBuilder userInput = new StringBuilder();
        for(LetterBox LetterBoxNode : inputBuffer)
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

    /**
     * Removes child based on where it is in the grid pane
     */
    private void removeLastGridChild()
    {
        LetterBox removedChild = inputBuffer.removeLast();
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

    /*
    changes main scene UI to colorblind mode by switching style sheets
     */
    public void switchToColorBlind(ActionEvent actionEvent) {
         Object buttonObject = actionEvent.getSource();
         ToggleButton toggleButton = (ToggleButton) buttonObject;

         Scene currentScene = toggleButton.getScene();
         if(toggleButton.isSelected())
         {
             currentScene.getStylesheets().clear();
             currentScene.getStylesheets().add(getClass().getResource("/org/example/wordlemvp/ColorBlind.css").toExternalForm());
         }
         else {
             currentScene.getStylesheets().clear();
             currentScene.getStylesheets().add(getClass().getResource("/org/example/wordlemvp/MainScreen.css").toExternalForm());
         }
        recolorKeyboard();

    }
}

