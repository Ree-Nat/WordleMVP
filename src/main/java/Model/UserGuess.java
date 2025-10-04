package Model;

import java.util.HashMap;

public class UserGuess extends WordleWord {

    private boolean matches;

    /**
     * creates a UserGuess object
     * @param input A string containing the user's guess.
     */
    public UserGuess(String input)
    {
        super(input);
        matches = false;
    }

    /**
     * returns the string representation of user guess
     * @return a string containing the user's guess
     */
    public String getUserGuess()
    {
        return input;
    }

    /**
     * Checks if User guess equals to the wordle word
     * @param answer an WordleAnswer object containing the answer to the wordle game
     * @return a boolean that tells if the word guess matches with the answer.
     */
    public boolean equals(WordleAnswer answer)
    {
        String answerWord = answer.getString().toUpperCase();
        String guessWord = this.getString().toUpperCase();

        return answerWord.equals(guessWord);
    }

    public void setMatches(boolean bool)
    {
        matches = bool;
    }

    //returns an array of status to put in gui

    /**
     * gets the color hashMap corresponding the index of the column on the board and
     * the letter
     * @param answer a WordleAnswer object containing the answer to the wordle game.
     * @return a hashmap corresponding the index of the column on the board and the letter
     */
    public HashMap<Integer, LetterStatus> getColorMap(WordleAnswer answer)
    {
        HashMap<Integer, Character> guessMap = this.getStringMap();
        HashMap<Integer, Character> answerMapClone =  new HashMap<>(answer.getStringMap());
        HashMap<Integer, LetterStatus> result = new HashMap<>();
        HashMap<Character, Integer> currentCount = answer.getEmptyWordCount();
        HashMap<Character, Integer> answerLetterCount = answer.getAmountOfLetters();
        for(int index = 0; index < guessMap.size(); index++)
        {
            Character guessLetter = guessMap.get(index+1);
            Character answerLetter = answerMapClone.get(index+1);
            if (guessLetter == answerLetter) {
                int letterAmount = currentCount.get(guessLetter);
                int maxLetterAmount =  answerLetterCount.get(guessLetter);
                result.put(index, LetterStatus.GREEN);
                currentCount.put(guessLetter, letterAmount+1);
            }
            else if(answerMapClone.containsValue(guessLetter))
            {
                int letterAmount = currentCount.get(guessLetter);
                int maxLetterAmount =  answerLetterCount.get(guessLetter);
                if(letterAmount < maxLetterAmount){
                    result.put(index, LetterStatus.YELLOW);
                    currentCount.put(guessLetter, letterAmount+1);}
                else{ result.put(index,LetterStatus.BLACK);}
            }
            else
            {
                result.put(index, LetterStatus.BLACK);
            }
        }
        return result;
    }
}
