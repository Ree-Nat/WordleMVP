package Model;

import java.util.HashMap;

public class UserGuess extends WordleWord {

    private boolean matches;

    public UserGuess(String input)
    {
        super(input);
        matches = false;
    }

    public String getUserGuess()
    {
        return input;
    }

    public boolean equals(WordleAnswer answer)
    {
        String answerWord = answer.getString();
        String guessWord = this.getString();

        return answerWord.equals(guessWord);
    }

    public void setMatches(boolean bool)
    {
        matches = bool;
    }

    //returns an array of status to put in gui
    public HashMap<Integer, LetterStatus> compare(WordleAnswer answer)
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
