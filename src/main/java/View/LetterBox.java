package View;


import Model.LetterStatus;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//Creates java fx view for letterbox
public class LetterBox {

    private final Text textBox = new Text();
    public LetterBox(Character letter, LetterStatus letterStatus) {
        textBox.setText(letter.toString());
        textBox.getStyleClass().add("letterBox");
        switch (letterStatus) {
            case GREEN ->  textBox.setStyle("-fx-background-color: green");
            case YELLOW -> textBox.setStyle("-fx-background-color: yellow");
            case GREY ->  textBox.setStyle("-fx-background-color: grey");
            case BLACK ->  textBox.setStyle("-fx-background-color: #404040FF");
        }
    }

    public Text getLetterBox() {
        return textBox;
    }
}
