package Model;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WordleWordTest {

    @Test
    public void testLength()
    {
        try {
            WordleAnswer answer = new WordleAnswer("CUMMINGHAM");
        }
        catch (Exception e) {
           System.out.println(e.getClass().getName());
        }
        WordleAnswer answer = new WordleAnswer("RACER");
        String racer = answer.getString();
        int racer_length = racer.length();
        assertEquals(5, racer_length );
    }

    @Test
    public void testContains()
    {
        UserGuess guess = new UserGuess("RACER");
        boolean doesContain = guess.contains('A');
        assertTrue(doesContain);
        boolean doesNotContain = guess.contains('Z');
        assertFalse(doesNotContain);
    }

    @Test
    public void getLetterAmountDictTest()
    {
        UserGuess word = new UserGuess("RACER");
        HashMap<Character, Integer> wordMap = word.getAmountOfLetters();
        System.out.print(wordMap);
    }

    @Test
    public void getEmptyWordCountList()
    {
        UserGuess word = new UserGuess("RACER");
        HashMap<Character, Integer> wordMap = word.getEmptyWordCount();
        System.out.print(wordMap);
    }

    @Test
    public void TestCompare()
    {
        UserGuess guess = new UserGuess("ZLLLL");
        //1-3 green 4 = yellow 5 = yellow
        WordleAnswer answer = new WordleAnswer("LILLY");
        LetterStatus[] correctSequence = new LetterStatus[]{
                LetterStatus.GREEN,
                LetterStatus.GREEN, LetterStatus.GREEN,
                LetterStatus.YELLOW, LetterStatus.YELLOW};

        HashMap<Integer, LetterStatus> colorMap = guess.compare(answer);
        //Collections answerColorValues = (Collections) colorMap.values();
        System.out.println(colorMap);

    }
}