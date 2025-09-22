package Model;

import java.util.HashMap;

public abstract class WordleWord {
    protected String input;
    protected HashMap<Integer, Character> stringMap;
    private static final int MAX_LENGTH = 5;

    public WordleWord(String input)
    {
        if(input.length() > MAX_LENGTH)
        {
            throw new IllegalArgumentException("Wordle String should be of length 5, " +
                    " Curren length is " + input.length());
        }
        this.input = input;
        this.stringMap = new HashMap<Integer, Character>();

        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            stringMap.put(index, input.charAt(index - 1));
        }
    }

    public Boolean contains(char letter)
    {
        return stringMap.containsValue(letter);
    }

    public HashMap<Character, Integer> getAmountOfLetters()
    {
        HashMap<Character, Integer> amount_of_letters = new HashMap<>();
        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            amount_of_letters.put(stringMap.get(index), 0);
        }
        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            Character currentChar =  stringMap.get(index);
            int currentValue = amount_of_letters.get(currentChar);
            amount_of_letters.put(currentChar, currentValue + 1);
        }

        return amount_of_letters;
    }

    public HashMap<Character, Integer> getEmptyWordCount()
    {
        HashMap<Character, Integer> amount_of_letters = new HashMap<>();
        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            amount_of_letters.put(stringMap.get(index), 0);
        }

        return amount_of_letters;
    }

    public HashMap<Integer, Character>  getStringMap()
    {
        return stringMap;
    }

    public String getString()
    {
        return input;
    }


}
