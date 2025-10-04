package org.example.wordlemvp;

//import Controller.GameController;
import View.FXWordleGame;
import javafx.application.Application;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void launch(String[] args) throws IOException {
        Application.launch(FXWordleGame.class, args);
    }
}
