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
    private Button gameSelectButton;
    private AnimationControl animationControl;
    private ParameterAdjustmentControl parameterAdjustmentControl;

    public InputPanel(double xPos, double yPos, double width, double height,
                      EventHandler<ActionEvent> gameSelectHandler,
                      CellGrid cellGrid, List<GameParameter> params) {
        setLayoutX(xPos);
        setLayoutY(yPos);

        background = new Rectangle(width, height);
        background.setId("input-panel-bg");

        gameSelectButton = new Button(AppResources.GAME_SELECT_TITLE.getResource());
        gameSelectButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        gameSelectButton.setOnAction(gameSelectHandler);

        animationControl = new AnimationControl(cellGrid);

        parameterAdjustmentControl = new ParameterAdjustmentControl(350, 0, width - 350, height,
                params, cellGrid);

        panelBox = new HBox(calculatePadding(), gameSelectButton, animationControl, parameterAdjustmentControl);

        panelBox.setAlignment(Pos.CENTER);
        panelBox.setPrefWidth(width);

        getChildren().addAll(background, panelBox);
    }

    private double calculatePadding() {
        return 100;
    }
}
