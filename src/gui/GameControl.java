package gui;

import cellsociety_team13.AppResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameControl extends VBox {

    private Button gameSelectButton;
    private Button viewToggleButton;

    public GameControl(double height, EventHandler<ActionEvent> gameSelectHandler,
                       EventHandler<ActionEvent> viewToggleHandler) {
        super(AppResources.INPUT_PANEL_PADDING.getDoubleResource());
        setPrefHeight(height);

        gameSelectButton = new Button(AppResources.GAME_SELECT_TITLE.getResource());
        gameSelectButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        gameSelectButton.setOnAction(gameSelectHandler);

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

        getChildren().addAll(gameSelectButton, viewToggleButton);
    }

}
