package gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Provides a simple box for displaying the title of the game.
 * Consists of a Text element overlayed on top of a background box.
 */
public class TitleBox extends Group {
    private static final double TEXT_VERTICAL_ALIGNMENT_RATIO = 0.625;

    private Rectangle background;
    private Text titleText;

    public TitleBox(double width, double height, String title) {
        background = new Rectangle(width, height);
        background.setId("title-bg");

        titleText = new Text(title);
        titleText.setId("title-text");
        titleText.setLayoutY(height * TEXT_VERTICAL_ALIGNMENT_RATIO);
        titleText.setWrappingWidth(width);

        getChildren().addAll(background, titleText);
    }
}
