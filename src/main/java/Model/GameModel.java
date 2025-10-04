package Model;

public class GameModel{
    private int current_guesses;
    private int MAX_GUESSES = 6;


    /**
     * Creates a game model object representing the
     * current state of the wordle game.
     */
    public GameModel(){
        this.current_guesses = 0;
    }

    /**
     * increments the current guesses by adding 1
     */
    public void increaseGuess()
    {
        current_guesses +=1;
    }

    /**
     * resets the current guess to zero
     */
    public void resetGuess()
    {
        current_guesses = 0;
    }

    /**
     * checks to see if current GameModel object guesses had reached max
     * @return true if max guess reaches max guesses.
     */
    public boolean reachMaxGuess()
    {
        return current_guesses == MAX_GUESSES;
    }

}
