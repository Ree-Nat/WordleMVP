# Wordle Clone MVP

##Video Demo 
- https://www.youtube.com/watch?v=jYxHkdUorJo

## Team Members
- Nathan Rinon (Ree-Nat)

## How to Run
1. Clone repository: `git clone git@github.com:Ree-Nat/WordleMVP.git`
2. Open in IntelliJ
3. [If JavaFX] Configure VM options: `--module-path [path] --add-modules javafx.controls`
4. Go to src/main/java/org/example/wordlemvp
5. Run `Launcher.java`

## Features Implemented
- Save and load feature: Allows previous game to be saved and load
- Keyboard recoloring and Word: shows which words are currently in the answer via colors
- Color-blind feature: allows red-green color blindness color palette.

## Controls
- Enter: Input word in grid
- Delete: Delete current word
- Keyboard: Input current key

## Known Issues
- Issue 1: UI keyboard does not change color but word grid UI elements do.
  - Solved by removing Style class from fxml editor
- Issue 2: Typing in keyboard does not work but on screen keyboard UI responds
 to input
  - Solved by setting focus traversable true in fxml editor using scenebuilder

## External Libraries
- Gson 2.13.1 (JSON parsing)
- JUnit 5.12.1 (testing)
- JavaFX 21.0.6 (UI)
