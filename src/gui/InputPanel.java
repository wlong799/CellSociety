// This entire file is part of my masterpiece.
// Will Long

package gui;

import cellsociety_team13.AppResources;
import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * InputPanel is located at the bottom of the overall Cell Society GUI. It
 * controls the various elements responsible for allowing the user to interact
 * with the application. It sets the position of the elements, and sends them
 * the information necessary to react with the rest of the application
 * correctly.
 *
 * I chose this class, because it is a good example of encapsulation. InputPanel
 * doesn't know how the details of each individual control element are
 * implemented, but it can still organize them and instantiate them in a way
 * that allows them to work properly. Each class is responsible for a specific
 * function.
 */
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

        createBackground(width, height);
        createControlFeatures(width, height, gameSelectHandler, viewToggleHandler,
                cellGrid, cellTypeChart, params);

        getChildren().addAll(background, panelBox);
    }

    /**
     * Calculates padding necessary to center all elements in the panel with equal spacing
     * between them.
     *
     * @param width is the width of the panel.
     * @return is padding necessary for equal spacing.
     */
    private double calculatePadding(double width) {
        double usedSpace = gameControl.getControlWidth() +
                animationControl.getControlWidth() +
                parameterAdjustmentControl.getControlWidth();

        double freeSpace = width - usedSpace;

        return freeSpace / 4;
    }

    private void createBackground(double width, double height) {
        background = new Rectangle(width, height);
        background.setId("input-panel-bg");
    }

    private void createControlFeatures(double width, double height, EventHandler<ActionEvent> gameSelectHandler,
                                       EventHandler<ActionEvent> viewToggleHandler, CellGrid cellGrid,
                                       CellTypeChart cellTypeChart, List<GameParameter> params) {

        gameControl = new GameControl(height, gameSelectHandler, viewToggleHandler);
        animationControl = new AnimationControl(height, cellGrid, cellTypeChart);
        parameterAdjustmentControl = new ParameterAdjustmentControl(height, params, cellGrid);

        panelBox = new HBox(calculatePadding(width), gameControl, animationControl, parameterAdjustmentControl);
        panelBox.setAlignment(Pos.CENTER);
        panelBox.setPrefWidth(width);
    }
}
