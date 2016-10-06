// This entire file is part of my masterpiece.
// Will Long

package gui;

import cellsociety_team13.AppResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * GameControl contains two buttons in a simple vertical box element. It takes
 * in two EventHandlers to allow it to communicate back with the main GUI.
 * gameSelectButton tells the main GUI to exit the current game and load a new
 * one. viewToggleButton tells the GUI to switch between grid and chart views.
 *
 * See InputPanel and ParameterAdjustmentControl for why I selected this class.
 */
public class GameControl extends VBox {

    private Button gameSelectButton;
    private Button viewToggleButton;

    public GameControl(double height, EventHandler<ActionEvent> gameSelectHandler,
                       EventHandler<ActionEvent> viewToggleHandler) {
        super(AppResources.INPUT_PANEL_PADDING.getDoubleResource());
        setPrefHeight(height);

        createGameSelectButton(gameSelectHandler);
        createViewToggleButton(viewToggleHandler);

        getChildren().addAll(gameSelectButton, viewToggleButton);
    }

    private void createGameSelectButton(EventHandler<ActionEvent> gameSelectHandler) {
        gameSelectButton = new Button(AppResources.GAME_SELECT_TITLE.getResource());
        gameSelectButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        gameSelectButton.setOnAction(gameSelectHandler);
    }

    private void createViewToggleButton(EventHandler<ActionEvent> viewToggleHandler) {
        viewToggleButton = new Button(AppResources.CHART_VIEW_TITLE.getResource());
        viewToggleButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        viewToggleButton.setOnAction(event -> {
            viewToggleHandler.handle(event);
            if (viewToggleButton.getText().equals(AppResources.CHART_VIEW_TITLE.getResource())) {
                viewToggleButton.setText(AppResources.GRID_VIEW_TITLE.getResource());
            } else {
                viewToggleButton.setText(AppResources.CHART_VIEW_TITLE.getResource());
            }
        });
    }

    public double getControlWidth() {
        return gameSelectButton.getPrefWidth();
    }

}
