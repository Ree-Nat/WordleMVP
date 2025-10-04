package View;


import Model.LetterStatus;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



//Creates java fx view for letterbox
public class LetterBox {

    StackPane stackpane = new StackPane();
    Rectangle letterBox = new Rectangle(50,50);
    private final Text textBox = new Text();

    /**
     * Creates a letterbox UI element object that is fitted in a stackpane to be added to a grid pane
     * @param letter a Character that the letterbox will have in the gridpane
     * @param letterStatus the status of current letter corresponding to how it will be colored
     */
    public LetterBox(Character letter, LetterStatus letterStatus) {

        stackpane.getChildren().addAll(letterBox, textBox);
        letterBox.getStyleClass().add("letterBox_Grey");
        stackpane.setAlignment(Pos.CENTER);
        textBox.getStyleClass().add("letterbox_text");
        textBox.setText(letter.toString());
        switch (letterStatus) {
            case GREEN ->  letterBox.getStyleClass().add("letterBox_Green");
            case YELLOW -> letterBox.getStyleClass().add("letterBox_Yellow");
            case GREY ->  letterBox.getStyleClass().add("letterBox_Grey");
            case BLACK ->  letterBox.getStyleClass().add("letterBox_Black");
        }
    }

    /**
     * Gets character of letter box
     * @return a character that corresponds to the letter in the box
     */
    public Character getLetterBoxCharacter() {
        return textBox.getText().charAt(0);
    }

    /**
     * Gets the stack pane container of the letter box to be added to the Grid pane.
     * We use a stack pane to help with centering the content
     * @return a stack pane object containing the letterbox
     */
    public StackPane getLetterBoxContainer() {return stackpane;}
}
