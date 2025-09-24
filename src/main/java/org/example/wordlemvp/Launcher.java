package org.example.wordlemvp;

import Controller.GameController;
import Model.GameModel;
import javafx.application.Application;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        //Application.launch(HelloApplication.class, args);
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        gameController.startGameLoop();
    }
}
