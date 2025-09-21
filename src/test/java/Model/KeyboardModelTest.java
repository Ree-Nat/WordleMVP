package Model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardModelTest {

    @Test
    public void keyBoardSizeCorrectTest() {
        Keyboard keyboard = new Keyboard();
        HashMap<Character, LetterStatus> keyset = keyboard.getKeyMap();
        assertEquals(26, keyset.size());
    }

    @Test
    void markStatusTest() {
        Keyboard Keyboard = new Keyboard();
        Keyboard.markStatus('W', LetterStatus.GREEN);
        HashMap<Character, LetterStatus> keyset = Keyboard.getKeyMap();
        assertEquals(LetterStatus.GREEN, keyset.get('W'));
    }

     @Test
     void resetKeyboardStatus() {
         Keyboard Keyboard = new Keyboard();
         HashMap<Character, LetterStatus> keymap = Keyboard.getKeyMap();
         Boolean hasOther = false;
         for(int shift = 0; shift < 26; ++shift)
         {
             int letterA = 65;
             char nextChar = (char)(letterA + shift);
             Keyboard.markStatus(nextChar, LetterStatus.GREEN);
         }
         Keyboard.resetKeyboardStatus();
         for(int shift = 0; shift < 26; ++shift)
         {
             int letterA = 65;
             char nextChar = (char)(letterA + shift);
             if (keymap.get(nextChar) != LetterStatus.GREY)
             {
                 hasOther = true;
             }
         }
         assertEquals(false, hasOther);

     }


}

