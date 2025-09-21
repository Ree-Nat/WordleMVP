package Model;

import java.time.temporal.ValueRange;
import java.util.HashMap;

//class the uses models keyboard based on ascii
public class KeyboardModel {
    private HashMap<Character, LetterStatus> keyMap;

    public KeyboardModel(HashMap<Character, LetterStatus> keyMap) {
        for(int shift =0; shift < 27; shift++)
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

}
