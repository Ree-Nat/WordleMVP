package Model;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WordRepository {

    private HashMap<Integer, String> wordMap;
    private Set<String> wordSet;
    private int seed;
    private int size;

    /**
     * Creates a Wordle repository object containing the possible words in the wordle game
     * @param filePath a string that leads to a json file containing the possible words
     * @throws IOException if filePath does not exist or file
     */
    public WordRepository(String filePath) throws IOException {
        wordMap = new HashMap<>();
        wordSet = new HashSet<>();
        populateList(filePath);
    }


    /**
     * Populates the repository's word list into a set for easy access
     * @param filePath a string that leads to a json file containing the possible words
     * @throws IOException if filePath does not exist or file
     */
    public void populateList(String filePath) throws IOException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        Object jsonFile = gson.fromJson(br, Object.class);

        String result = jsonFile.toString();
        JsonElement element = JsonParser.parseString(result);
        JsonObject obj = element.getAsJsonObject();
        wordSet = obj.keySet();

        HashMap<Integer,String> tmpWordMap = new HashMap<>();

        int index = 1;
        for (String word : wordSet){
            tmpWordMap.put(index, word.toUpperCase());
            index++;
        }
        setWordMap(tmpWordMap);
        this.size = wordSet.size();


    }

    /**
     * checks to see if word exists in the repository
     * @param word a User guess containing the word to be found
     * @return a boolean true if the word exists.
     */
    public Boolean exists(UserGuess word)
    {
        String guessWord = word.getString();
        return wordSet.contains(guessWord.toLowerCase());
    }

    /**
     * checks to see if string word exists in the repository
     * @param word a string containing the word to be found
     * @return a boolean true if the word exists.
     */
    public Boolean exists(String word)
    {
        return wordSet.contains(word.toLowerCase());
    }

    /**
     * Gets a random word from the repository using system time
     * @return a String object representing the random word
     */
    public String getRandomWord()
    {
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        int randomInt = rand.nextInt(size - 1 + 1) + 1;
        return wordMap.get(randomInt);
    }

    /**
     * Gets size of repository
     * @return int repository size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Sets current word map of current object
     * @param wordMap a wordmap to set to object's data
     */
    private void setWordMap(HashMap<Integer, String> wordMap)
    {
        this.wordMap = wordMap;
    }


}
