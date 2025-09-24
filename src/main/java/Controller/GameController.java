package Controller;

import Model.*;
import javafx.animation.ScaleTransition;

import java.io.IOException;
import java.util.*;


//
public class GameController {


    private final Keyboard keyboard;
    private WordleBoard wordleBoard;
    private final GameModel gameModel;
    private final WordList wordList;
    private Boolean winStatus;


    public GameController(GameModel gameModel) throws IOException {
        {
            keyboard = new Keyboard();
            wordList = new WordList("src/main/resources/Valid_Wordle_Words.json");
            this.gameModel = gameModel;
        }
    }

    public void startGameLoop()
    {
        long seed= System.currentTimeMillis();
        Random random = new Random(seed);
        WordleAnswer wordleAnswer = new WordleAnswer(wordList.getRandomWord(seed));

        while(!gameModel.reachMaxGuess() && winStatus == false)
        {
            System.out.println("WELCOME TO WORDLE MVP GUESS 5 WORDS");
            UserGuess userGuess = getInput();
            assert wordleBoard != null;

            wordleBoard.addWord(userGuess);
            HashMap<Integer, LetterStatus> resultOutput = userGuess.compare(wordleAnswer);
            winStatus = checkWin(resultOutput);
            keyboard.updateKeyboardStatus(resultOutput, userGuess);
            gameModel.increaseGuess();
        }
        if(winStatus == false) {
            System.out.println("GAME OVER, WORD IS" + wordleAnswer.getString());
        }
        else {
            System.out.println("YOU WON, WORD IS " + wordleAnswer.getString());
        }
    }

    private boolean checkWin(HashMap<Integer, LetterStatus> output)
    {
        LetterStatus[] colors = LetterStatus.values();
        List<LetterStatus> letterList = new ArrayList<>(Arrays.asList(colors));
        return !letterList.contains(LetterStatus.YELLOW) && !letterList.contains(LetterStatus.BLACK);
    }

    private UserGuess getInput()
    {
        boolean isValid = false;
        Scanner scanner = new Scanner(System.in);
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
       scanner.close();
        return new UserGuess(input);
    }
}