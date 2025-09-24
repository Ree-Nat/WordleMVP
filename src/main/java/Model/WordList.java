package Model;
import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WordList {

    String randomWord;
    HashMap<Integer, String> wordMap;
    Set<String> wordSet;
    private int seed;
    private int size;

    public WordList(String filePath) throws IOException {
        populateList(filePath);
    }


    public void populateList(String filePath) throws IOException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        Object jsonFile = gson.fromJson(br, Object.class);

        String result = jsonFile.toString();
        JsonElement element = JsonParser.parseString(result);
        JsonObject obj = element.getAsJsonObject();
        wordSet = obj.keySet();

        wordMap = new HashMap<>();
        int index = 1;
        for (String word : wordSet){
            wordMap.put(index, word);
        }
        size = wordSet.size();

        br.close();
    }

    public Boolean exists(UserGuess word)
    {
        String guessWord = word.getString();
        return wordSet.contains(guessWord);
    }

    public Boolean exists(String word)
    {
        return wordSet.contains(word);
    }

    public String getRandomWord(long seed)
    {
        Random rand = new Random(seed);
        randomWord = wordMap.get(rand.nextInt(size));
        return randomWord;
    }

    public int getSize()
    {
        return size;
    }

}
