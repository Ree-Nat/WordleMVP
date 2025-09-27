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

    public LetterBox(Character letter, LetterStatus letterStatus) {

        stackpane.getChildren().addAll(letterBox, textBox);
        letterBox.setFill(Color.GREY);
        stackpane.setAlignment(Pos.CENTER);
        textBox.getStyleClass().add("letterbox_text");
        textBox.setText(letter.toString());
        switch (letterStatus) {
            case GREEN ->  letterBox.setStyle("-fx-fill: #097809");
            case YELLOW -> letterBox.setStyle("-fx-fill: #efd24b");
            case GREY ->  letterBox.setStyle("-fx-fill: #919191");
            case BLACK ->  letterBox.setStyle("-fx-fill: #202020");
        }
    }

    public Character getLetterBoxCharacter() {
        return textBox.getText().charAt(0);
    }

    public StackPane getLetterBoxContainer() {return stackpane;}
}
