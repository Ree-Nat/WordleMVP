package Model;

import java.util.HashMap;

public abstract class WordleWord {
    protected String input;
    protected HashMap<Integer, Character> stringMap;
    private static final int MAX_LENGTH = 5;


    /**
     * Creates a wordle word object.
     * @param input an input of size 5
     */
    public WordleWord(String input)
    {
        if(input.length() != MAX_LENGTH)
        {
            throw new IllegalArgumentException("Wordle String should be of length 5, " +
                    " Curren length is " + input.length());
        }
        this.input = input.toUpperCase();
        this.stringMap = new HashMap<Integer, Character>();

        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            stringMap.put(index, input.charAt(index - 1));
        }
    }

    /**
     * Checks to see if the wordle word contains a letter.
     * @param letter a character to be found in the wordle object
     * @return a boolean detecting whether a character is found
     */
    public Boolean contains(char letter)
    {
        return stringMap.containsValue(letter);
    }

    /**
     * Gets a hashmap checking the current instance of each letter
     * @return a hashmap corresponding to the instance of each word in the wordle word.
     */
    public HashMap<Character, Integer> getAmountOfLetters()
    {
        HashMap<Character, Integer> amount_of_letters = this.getEmptyWordCount();
        char[] stringList = input.toCharArray();

        for(Character character: stringList)
        {
           int character_count = amount_of_letters.get(character);
           amount_of_letters.put(character, character_count + 1);
        }
        return amount_of_letters;
    }

    /**
     * Creates a hasmap containing the letter instance with all values set to 0
     * @return a Hashamp containing a letter list with its instance set to zero
     */
    public HashMap<Character, Integer> getEmptyWordCount()
    {
        HashMap<Character, Integer> amount_of_letters = new HashMap<>();
        for(int index = 1; index <= MAX_LENGTH; index++)
        {
            amount_of_letters.put(stringMap.get(index), 0);
        }

        return amount_of_letters;
    }

    /**
     * gets a map corresponding to the place on the wordle column and its letter
     * @return a hashmap corresponding to the place on the wordle column and its letter
     */
    public HashMap<Integer, Character>  getStringMap()
    {
        return stringMap;
    }

    /**
     * Gets the current string of the wordle word
     * @return a String corresponding to the wordle word.
     */
    public String getString()
    {
        return input;
    }


}
