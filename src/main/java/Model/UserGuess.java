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

    public boolean isMatch()
    {
        return matches;
    }

    public void setMatches(boolean bool)
    {
        matches = bool;
    }


}
