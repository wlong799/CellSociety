package gui;

import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class InputPanel extends Group {
    private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final double ARC_SIZE = 50;

    private Rectangle background;
    private XMLFileControl xmlFileControl;
    private AnimationControl animationControl;
    private ParameterAdjustmentControl parameterAdjustmentControl;

    public InputPanel(double x, double y, double width, double height,
                      EventHandler<ActionEvent> submitFileHandler,
                      CellGrid cellGrid, List<GameParameter> params) {
        setLayoutX(x);
        setLayoutY(y);

        background = new Rectangle(width, height);
        background.setFill(BACKGROUND_COLOR);
        background.setArcWidth(ARC_SIZE);
        background.setArcHeight(ARC_SIZE);

        xmlFileControl = new XMLFileControl(0, 0, 200, height, submitFileHandler);

        animationControl = new AnimationControl(225, 0, 100, height, cellGrid);

        parameterAdjustmentControl = new ParameterAdjustmentControl(350, 0, width - 350, height,
                params, cellGrid);

        getChildren().addAll(background, xmlFileControl,
                animationControl, parameterAdjustmentControl);
    }

    public String getXMLFilename() {
        return xmlFileControl.getFilename();
    }
}
