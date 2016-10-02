package gui;

import cellsociety_team13.AppResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GameSelect extends Button {

    public GameSelect(double width, EventHandler<ActionEvent> gameSelectHandler) {
        super(AppResources.GAME_SELECT_TITLE.getResource());
        setPrefWidth(width);
        setOnAction(gameSelectHandler);
    }
}
