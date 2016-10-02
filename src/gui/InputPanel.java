package gui;

import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class InputPanel extends Group {
    private Rectangle background;
    private HBox panelBox;
    private GameSelect gameSelect;
    private AnimationControl animationControl;
    private ParameterAdjustmentControl parameterAdjustmentControl;

    public InputPanel(double xPos, double yPos, double width, double height,
                      EventHandler<ActionEvent> submitFileHandler,
                      CellGrid cellGrid, List<GameParameter> params) {
        setLayoutX(xPos);
        setLayoutY(yPos);

        background = new Rectangle(width, height);
        background.setId("input-panel-bg");

        gameSelect = new GameSelect(200, submitFileHandler);

        animationControl = new AnimationControl(225, 0, 100, height, cellGrid);

        parameterAdjustmentControl = new ParameterAdjustmentControl(350, 0, width - 350, height,
                params, cellGrid);

        panelBox = new HBox(gameSelect, animationControl, parameterAdjustmentControl);

        panelBox.setAlignment(Pos.CENTER);
        panelBox.setPrefWidth(width);

        getChildren().addAll(background, panelBox);
    }
}
