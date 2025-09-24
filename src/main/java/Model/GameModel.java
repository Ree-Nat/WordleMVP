package Model;

public class GameModel{
    private int current_guesses;

    public GameModel(){
        this.current_guesses = 0;
    }

    public void increaseGuess()
    {
        current_guesses +=1;
    }

    public void resetGuess()
    {
        current_guesses = 0;
    }

    public boolean reachMaxGuess()
    {
        return current_guesses == 6;
    }

}
