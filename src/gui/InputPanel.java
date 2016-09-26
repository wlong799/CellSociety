package gui;

import cellsociety_team13.CellGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InputPanel extends Group {
    private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final double ARC_SIZE = 50;

    private Rectangle background;
    private TextField xmlFilenameField;
    private Button submitFileButton;
    private AnimationControl animationControl;
    private ComboBox parameterAdjustmentBox;

    public InputPanel(double x, double y, double width, double height,
                      EventHandler<ActionEvent> submitFileHandler,
                      CellGrid cellGrid, String[] params) {
        setLayoutX(x);
        setLayoutY(y);

        background = new Rectangle(width, height);
        background.setFill(BACKGROUND_COLOR);
        background.setArcWidth(ARC_SIZE);
        background.setArcHeight(ARC_SIZE);

        xmlFilenameField = new TextField("XML GAME FILE");
        xmlFilenameField.setMinWidth(200);
        xmlFilenameField.setMaxWidth(200);

        submitFileButton = new Button("SUBMIT");
        submitFileButton.setLayoutX(225);
        submitFileButton.setMinWidth(100);
        submitFileButton.setMaxWidth(100);
        submitFileButton.setOnAction(submitFileHandler);

        animationControl = new AnimationControl(350, 0, 100, height, cellGrid);

        parameterAdjustmentBox = new ComboBox();
        parameterAdjustmentBox.setLayoutX(500);
        parameterAdjustmentBox.setMinWidth(width - 500);
        parameterAdjustmentBox.setMaxWidth(width - 500);
        for (String param : params) {
            parameterAdjustmentBox.getItems().add(param);
        }
        getChildren().addAll(background, xmlFilenameField, submitFileButton,
                animationControl, parameterAdjustmentBox);
    }

    public String getXMLFilename() {
        return xmlFilenameField.getCharacters().toString();
    }


}
