package Model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordFetcherTest {


    WordFetcherTest() throws IOException {
    }

    //checks to see if wordlist is populated without exceptions
    @Test
    void PopulateWordList() throws IOException {
        String filepath = "src/main/resources/Valid_Wordle_Words.json";
        WordRepository fetcherGenerator = new WordRepository(filepath);
    }

    void CheckWordMapSize() throws IOException {
        String filepath = "src/main/resources/Valid_Wordle_Words.json";
        WordRepository fetcherGenerator = new WordRepository(filepath);
        int wordListSize = fetcherGenerator.getSize();
        assertEquals(15917, wordListSize);
    }

    @Test
    void CheckWordExists() throws IOException {
        String filepath = "src/main/resources/Valid_Wordle_Words.json";
        WordRepository fetcherGenerator = new WordRepository(filepath);
        UserGuess guessWord = new UserGuess("rabid");
        boolean exists = fetcherGenerator.exists(guessWord);
        assertTrue(exists);

    }

}