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

public class WordList {

    private HashMap<Integer, String> wordMap;
    private Set<String> wordSet;
    private int seed;
    private int size;

    public WordList(String filePath) throws IOException {
        wordMap = new HashMap<>();
        wordSet = new HashSet<>();
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

        HashMap<Integer,String> tmpWordMap = new HashMap<>();

        int index = 1;
        for (String word : wordSet){
            tmpWordMap.put(index, word);
            index++;
        }
        setWordMap(tmpWordMap);
        this.size = wordSet.size();


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
        int randomInt = rand.nextInt(size - 1 + 1) + 1;
        return wordMap.get(randomInt);
    }

    public int getSize()
    {
        return size;
    }

    public void setWordMap(HashMap<Integer, String> wordMap)
    {
        this.wordMap = wordMap;
    }

    public void setWordSet(Set<String> wordSet)
    {
        this.wordSet = wordSet;
    }

}
