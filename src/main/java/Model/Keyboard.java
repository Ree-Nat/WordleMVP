package Model;

import java.time.temporal.ValueRange;
import java.util.HashMap;

//class the uses models keyboard based on ascii
public class Keyboard {
    private final HashMap<Character, LetterStatus> keyMap;

    public Keyboard() {

        keyMap = new HashMap<Character, LetterStatus>();

        for(int shift =0; shift < 26; shift++)
        {
            int letterA = 65;
            char nextChar = (char)(letterA + shift);
            keyMap.put(nextChar, LetterStatus.GREY);
        }
    }
    public void resetKeyboardStatus()
    {
        keyMap.replaceAll((key, value) -> value = LetterStatus.GREY);
    }

    public void markStatus(Character letter, LetterStatus status)
    {
        LetterStatus e = keyMap.get(letter);
        if (status == null)
        {
            throw new IllegalArgumentException("Input must be of letter type");
        }
        keyMap.replace(letter, status);
    }

    public void updateKeyboardStatus(HashMap<Integer, LetterStatus> colorMap, UserGuess userGuess)
    {
        String guessString = userGuess.getString();
        guessString = guessString.toUpperCase();
        for(int index = 0; index < colorMap.size(); index++)
        {
            Character currentLetter= guessString.charAt(index);
            LetterStatus keyLetterStatus = keyMap.get(currentLetter);
            LetterStatus outputLetterStatus = colorMap.get(index);


            if((keyLetterStatus == LetterStatus.GREY)
            && (outputLetterStatus == LetterStatus.GREEN || outputLetterStatus == LetterStatus.YELLOW))
                {
                    this.markStatus(currentLetter, outputLetterStatus);
                }
            else if(outputLetterStatus == LetterStatus.GREEN)
            {
                this.markStatus(currentLetter, LetterStatus.GREEN);
            }
            else if(outputLetterStatus == LetterStatus.BLACK && keyLetterStatus != LetterStatus.GREEN || keyLetterStatus!= LetterStatus.YELLOW)
            {
                this.markStatus(currentLetter, outputLetterStatus);
            }
        }

    }

    public HashMap<Character, LetterStatus> getKeyMap()
    {
        return keyMap;
    }

}
