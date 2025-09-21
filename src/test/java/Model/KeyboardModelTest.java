package Model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardModelTest {

    @Test
    public void keyBoardSizeCorrectTest() {
        KeyboardModel keyboard = new KeyboardModel();
        HashMap<Character, LetterStatus> keyset = keyboard.getKeyMap();
        assertEquals(26, keyset.size());
    }

    @Test
    void markStatusTest() {
        KeyboardModel keyboardModel = new KeyboardModel();
        keyboardModel.markStatus('W', LetterStatus.GREEN);
        HashMap<Character, LetterStatus> keyset = keyboardModel.getKeyMap();
        assertEquals(LetterStatus.GREEN, keyset.get('W'));
    }

     @Test
     void resetKeyboardStatus() {
         KeyboardModel keyboardModel = new KeyboardModel();
         HashMap<Character, LetterStatus> keymap = keyboardModel.getKeyMap();
         Boolean hasOther = false;
         for(int shift = 0; shift < 26; ++shift)
         {
             int letterA = 65;
             char nextChar = (char)(letterA + shift);
             keyboardModel.markStatus(nextChar, LetterStatus.GREEN);
         }
         keyboardModel.resetKeyboardStatus();
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

