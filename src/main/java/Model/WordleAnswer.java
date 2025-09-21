package Model;

import java.util.HashMap;

public class WordleAnswer extends WordleWord{

    private static final int MAX_LENGTH = 5;

    public WordleAnswer(String input)
    {
        super(input);
    }


    public HashMap<Integer, Character> getMapping() {
        return stringMap;
    }

}
