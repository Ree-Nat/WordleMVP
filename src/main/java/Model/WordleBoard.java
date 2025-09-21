package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WordleBoard {
    int MAX_ROWS = 6;
    ArrayList<String> wordGrid;


    public WordleBoard(Keyboard keyboard)
    {
        wordGrid = new ArrayList<String>();
    }

    public void addWord(UserGuess guess)
    {
        String guess_word = guess.getUserGuess();
        wordGrid.add(guess_word);

    }

    public String getRow(int index)
    {
        int search_index = index - 1;
        return wordGrid.get(search_index);
    }

    public void clearBoard()
    {
        for(int x = 0; x < wordGrid.toArray().length; x++)
        {
            wordGrid.removeFirst();
        }
    }






}
