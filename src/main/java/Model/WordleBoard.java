package Model;

import java.util.ArrayList;
import java.util.List;

public class WordleBoard{
    private final int MAX_ROWS = 6;
    ArrayList<String> wordList;


    /**
     * Creates a wordle board object containing an arraylist populated
     * by the user's guess
     */
    public WordleBoard()
    {
        wordList = new ArrayList<String>();
    }

    /**
     * Adds a word to the word list
     * @param guess a string to be added to the wordlist
     */
    public void addWord(UserGuess guess)
    {
        String guess_word = guess.getUserGuess();
        wordList.add(guess_word);

    }



    /**
     * Resets the board list
     */
    public void resetBoard()
    {
        wordList.clear();
    }

    /**
     * Checks to see if board is full
     * @return a boolean representing if the board is full
     */
    public boolean isFull()
    {
        return wordList.size() == 6;
    }

    /**
     * Gets current size of board
     * @return a int representing the current size of the board
     */
    public int getCurrentSize()
    {
        return wordList.size();
    }

    /**
     * gets an arraylist corresponding the current items on the board
     * @return an arraylist corresponding the current items on the board
     */
    public ArrayList<String> getWordList()
    {
        return wordList;
    }

    /**
     * Gets the maximum size of the board
     * @return an int corresponding the maximum size of the board.
     */
    public int getMaxSize()
    {
        return MAX_ROWS;
    }

}
