package Model;

import java.util.HashMap;

public abstract class WordleWord {
    protected String input;
    protected HashMap<Integer, Character> stringMap;

    public WordleWord(String input)
    {
        this.input = input;
        this.stringMap = new HashMap<Integer, Character>();
    }

    public Boolean contains(char letter)
    {
        return stringMap.containsValue(letter);
    }


}
