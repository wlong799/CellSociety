package gui;

import cellsociety_team13.CellGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InputPanel extends Group {
    private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final double ARC_SIZE = 50;

    private Rectangle background;
    private XMLFileControl xmlFileControl;
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

        xmlFileControl = new XMLFileControl(0, 0, 200, height, submitFileHandler);

        animationControl = new AnimationControl(225, 0, 100, height, cellGrid);

        parameterAdjustmentBox = new ComboBox();
        parameterAdjustmentBox.setLayoutX(350);
        parameterAdjustmentBox.setMinWidth(width - 350);
        parameterAdjustmentBox.setMaxWidth(width - 350);
        for (String param : params) {
            parameterAdjustmentBox.getItems().add(param);
        }
        getChildren().addAll(background, xmlFileControl,
                animationControl, parameterAdjustmentBox);
    }

    public String getXMLFilename() {
        return xmlFileControl.getFilename();
    }
    
    
    public String getSelectedString(){
    	return (String) parameterAdjustmentBox.getValue();
    }


}
