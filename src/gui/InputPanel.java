package gui;

import cellsociety_team13.AppResources;
import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class InputPanel extends Group {
    private Rectangle background;
    private HBox panelBox;
    private GameControl gameControl;
    private AnimationControl animationControl;
    private ParameterAdjustmentControl parameterAdjustmentControl;

    public InputPanel(double xPos, double yPos, double width, double height,
                      EventHandler<ActionEvent> gameSelectHandler,
                      EventHandler<ActionEvent> viewToggleHandler,
                      CellGrid cellGrid, CellTypeChart cellTypeChart, List<GameParameter> params) {
        setLayoutX(xPos);
        setLayoutY(yPos);

        background = new Rectangle(width, height);
        background.setId("input-panel-bg");

        gameControl = new GameControl(height, gameSelectHandler, viewToggleHandler);
        animationControl = new AnimationControl(height, cellGrid, cellTypeChart);
        parameterAdjustmentControl = new ParameterAdjustmentControl(height, params, cellGrid);

        panelBox = new HBox(calculatePadding(width), gameControl, animationControl, parameterAdjustmentControl);
        panelBox.setAlignment(Pos.CENTER);
        panelBox.setPrefWidth(width);

        getChildren().addAll(background, panelBox);
    }
    

    private double calculatePadding(double width) {
        double usedSpace = AppResources.INPUT_BUTTON_WIDTH.getDoubleResource();
        usedSpace += (2 * AppResources.INPUT_BUTTON_WIDTH.getDoubleResource()) +
                AppResources.INPUT_PANEL_PADDING.getDoubleResource();
        usedSpace += AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource();

        double freeSpace = width - usedSpace;

        return freeSpace / 4;
    }
}
