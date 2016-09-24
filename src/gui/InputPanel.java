package gui;

import cellsociety_team13.CellGrid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class InputPanel extends Group {
    private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final double ARC_SIZE = 50;
    private static final int RUN_SPEED_MILLI = 500;

    private CellGrid targetCellGrid;
    private Timeline runAnimation;

    private Rectangle background;
    private TextField xmlFilenameField;
    private Button submitFileButton, stepButton, runButton;
    private ComboBox parameterAdjustmentBox;

    public InputPanel(double x, double y, double width, double height,
                      EventHandler<ActionEvent> submitFileHandler,
                      CellGrid cellGrid, String[] params) {
        targetCellGrid = cellGrid;

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

        stepButton = new Button("STEP");
        stepButton.setLayoutX(350);
        stepButton.setMinWidth(50);
        stepButton.setMaxWidth(50);
        stepButton.setOnAction(e -> targetCellGrid.step());

        KeyFrame frame = new KeyFrame(Duration.millis(RUN_SPEED_MILLI),
                e -> step());
        runAnimation = new Timeline();
        runAnimation.setCycleCount(Timeline.INDEFINITE);
        runAnimation.getKeyFrames().add(frame);

        runButton = new Button("RUN");
        runButton.setLayoutX(425);
        runButton.setMinWidth(50);
        runButton.setMaxWidth(50);
        runButton.setOnAction(e -> toggleRunning());

        parameterAdjustmentBox = new ComboBox();
        parameterAdjustmentBox.setLayoutX(500);
        parameterAdjustmentBox.setMinWidth(width - 500);
        parameterAdjustmentBox.setMaxWidth(width - 500);
        for (String param : params) {
            parameterAdjustmentBox.getItems().add(param);
        }
        getChildren().addAll(background, xmlFilenameField, submitFileButton, stepButton,
                runButton, parameterAdjustmentBox);
    }

    public String getXMLFilename() {
        return xmlFilenameField.getCharacters().toString();
    }

    private void step() {
        targetCellGrid.step();
    }

    private void toggleRunning() {
        if (runAnimation.getStatus().equals(Animation.Status.RUNNING)) {
            runAnimation.pause();
            runButton.setText("RUN");
        } else {
            runAnimation.play();
            runButton.setText("PAUSE");
        }
    }
}
