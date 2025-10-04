package Model;

import java.time.temporal.ValueRange;
import java.util.HashMap;

public class Keyboard {
    private final HashMap<Character, LetterStatus> keyMap;

    /**
     * Creates a keyboard object by representing each letter with its
     * current status in a hashmap
     */
    public Keyboard() {

        keyMap = new HashMap<Character, LetterStatus>();

        for(int shift =0; shift < 26; shift++)
        {
            int letterA = 65;
            char nextChar = (char)(letterA + shift);
            keyMap.put(nextChar, LetterStatus.GREY);
        }
    }

    /**
     * Resets the keyboard status to gray.
     */
    public void resetKeyboardStatus()
    {
        keyMap.replaceAll((key, value) -> value = LetterStatus.GREY);
    }

    /**
     * Marks a current keyboard with a specific keyboard status
     * @param letter a character representing a key
     * @param status a enum representing the letter's status pertaining to its comparison with
     *               the wordle answer.
     */
    public void markStatus(Character letter, LetterStatus status)
    {
        LetterStatus e = keyMap.get(letter);
        if (status == null)
        {
            throw new IllegalArgumentException("Input must be of letter type");
        }
        keyMap.replace(letter, status);
    }

    /**
     * Updates the keyboard by indexing through user guess string
     * @param userGuess a UserGuess object containing user's guess
     * @param answer a WordleAnswer object containing the answer to the wordle game
     */
    public void updateKeyboardStatus(UserGuess userGuess, WordleAnswer answer)
    {

        HashMap<Integer, LetterStatus> colorMap = userGuess.getColorMap(answer);

        String guessString = userGuess.getString();
        guessString = guessString.toUpperCase();
        for(int index = 0; index < colorMap.size(); index++)
        {
            Character currentLetter= guessString.charAt(index);
            LetterStatus keyLetterStatus = keyMap.get(currentLetter);
            LetterStatus outputLetterStatus = colorMap.get(index);

            if ((keyLetterStatus == LetterStatus.GREEN || keyLetterStatus == LetterStatus.YELLOW) && outputLetterStatus == LetterStatus.BLACK){
                this.markStatus(currentLetter, keyLetterStatus);
            }
            else if((keyLetterStatus == LetterStatus.GREY)
            && (outputLetterStatus == LetterStatus.GREEN || outputLetterStatus == LetterStatus.YELLOW) || outputLetterStatus == LetterStatus.BLACK)
                {
                    this.markStatus(currentLetter, outputLetterStatus);
                }
            else if(outputLetterStatus == LetterStatus.GREEN &&
                    (keyLetterStatus == LetterStatus.YELLOW))
            {
                this.markStatus(currentLetter, LetterStatus.GREEN);
            }
        }

    }

    /**
     * gets a key map corresponding the keyboard key and it's status
     * @return a HashMap object containing the character mapped to its status on the keyboard
     */
    public HashMap<Character, LetterStatus> getKeyMap()
    {
        return keyMap;
    }

}
