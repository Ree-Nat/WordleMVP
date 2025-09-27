/*
package Controller;

import Model.*;
import javafx.animation.ScaleTransition;

import java.io.IOException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


//
public class GameController {


    private final Keyboard keyboard;
    private final WordleBoard wordleBoard;
    private final GameModel gameModel;
    private final WordList wordList;
    private Boolean winStatus;
    private WordleAnswer wordleAnswer;
    private String userInput;



    public GameController(GameModel gameModel) throws IOException {
        {
            this.winStatus = false;
            wordleBoard = new WordleBoard();
            keyboard = new Keyboard();
            wordList = new WordList("src/main/resources/Valid_Wordle_Words.json");
            this.gameModel = gameModel;
        }
    }

    public void startGameLoop()
    {
        long seed= System.currentTimeMillis();
        Random random = new Random(seed);
        String randomWord = wordList.getRandomWord(seed);
        wordleAnswer = new WordleAnswer(randomWord);
        System.out.println("WELCOME TO WORDLE MVP GUESS 6 WORDS");
        //Scanner scanner = new Scanner(System.in);
        while(!gameModel.reachMaxGuess() && winStatus == false)
        {

            //line waiting for input from MainSceneController

            wordleBoard.addWord(userGuess);
            HashMap<Integer, LetterStatus> resultOutput = userGuess.compare(wordleAnswer);
            winStatus = checkWin(resultOutput);
            keyboard.updateKeyboardStatus(resultOutput, userGuess);
            System.out.println(userGuess.toString());
            System.out.println(resultOutput.toString());
            gameModel.increaseGuess();

            //passes it back to MainSceneController
        }

        if(winStatus == false) {
            System.out.println("GAME OVER, WORD IS " + wordleAnswer.getString());
        }
        else {
            System.out.println("YOU WON, WORD IS " + wordleAnswer.getString());
        }
        //scanner.close();
    }


    public void processGuest()


    public WordleAnswer getWordleAnswer()
    {
        return this.wordleAnswer;
    }

    private boolean checkWin(HashMap<Integer, LetterStatus> output)
    {
        LetterStatus[] colors = LetterStatus.values();
        List<LetterStatus> letterList = new ArrayList<>(Arrays.asList(colors));
        return !letterList.contains(LetterStatus.YELLOW) && !letterList.contains(LetterStatus.BLACK);
    }

    //console app input
    /*
    private UserGuess getInput(Scanner scanner)
    {
        boolean isValid = false;
        String input;
        do{
            try{
                input = scanner.nextLine();
                if(wordList.exists(input))
                    {
                    isValid = true;
                    }
                else
                {
                    System.out.println("Word is not in List. Please try again.");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Input, Try Again");
            }
       }while(!isValid);
        return new UserGuess(input);
    }

}
   */