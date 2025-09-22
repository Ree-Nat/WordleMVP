package Model;

import java.util.HashMap;

public class WordleAnswer extends WordleWord{


    public WordleAnswer(String input)
    {
        super(input);
    }


    public HashMap<Integer, Character> getMapping() {
        return stringMap;
    }

}
