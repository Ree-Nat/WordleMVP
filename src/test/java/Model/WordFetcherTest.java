package Model;

import com.google.gson.JsonParser;
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
        WordList fetcherGenerator = new WordList(2313, filepath);
    }

    void CheckWordMapSize() throws IOException {
        String filepath = "src/main/resources/Valid_Wordle_Words.json";
        WordList fetcherGenerator = new WordList(2313, filepath);
        int wordListSize = fetcherGenerator.getSize();
        assertEquals(15917, wordListSize);
    }

}