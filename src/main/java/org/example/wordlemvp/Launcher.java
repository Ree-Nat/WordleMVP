package org.example.wordlemvp;

//import Controller.GameController;
import View.FXWordleGame;
import javafx.application.Application;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * Launches wordle game in the class FXWordleGame
     * @param args a String array object
     * @throws IOException if main scene and class does not exist
     */
    public static void launch(String[] args) throws IOException {
        Application.launch(FXWordleGame.class, args);
    }
}
