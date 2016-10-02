package gui;

import cellsociety_team13.AppResources;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * AppLauncher class for CellSociety. Basic boilerplate code for launching the JavaFX
 * application.
 */
public class AppLauncher extends Application {
    private CellSocietyGUI cellSocietyGUI;

    @Override
    public void start (Stage stage) {
        cellSocietyGUI = new CellSocietyGUI();
        stage.setTitle(AppResources.APP_TITLE.getResource());
        stage.setScene(cellSocietyGUI.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}