package gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TitleBox extends Group {
    private static final Color BACKGROUND_COLOR = Color.AZURE;
    private static final double ARC_SIZE = 50;
    private static final double TEXT_VERTICAL_ALIGNMENT_RATIO = 0.625;
    private static final String FONT_FAMILY = "Monospace";
    private static final int FONT_SIZE = 36;

    private Rectangle background;
    private Text titleText;

    public TitleBox(double x, double y, double width, double height, String title) {
        setLayoutX(x);
        setLayoutY(y);

        background = new Rectangle(width, height);
        background.setFill(BACKGROUND_COLOR);
        background.setArcHeight(ARC_SIZE);
        background.setArcWidth(ARC_SIZE);

        titleText = new Text(title);
        titleText.setLayoutY(height * TEXT_VERTICAL_ALIGNMENT_RATIO);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setWrappingWidth(width);
        titleText.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE));

        getChildren().add(background);
        getChildren().add(titleText);
    }

    public String getText() {
        return titleText.getText();
    }

    public void setText(String text) {
        titleText.setText(text);
    }
}
