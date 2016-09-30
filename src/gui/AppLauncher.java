package gui;

import cellsociety_team13.AppResources;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Properties;

/**
 * AppLauncher class for CellSociety. Basic boilerplate code for launching the JavaFX
 * application.
 */
public class AppLauncher extends Application {
    private static final String RESOURCE_FILENAME = "Main";
    private static final String APP_TITLE_KEY = "appTitle";
    private Properties properties;

    private CellSocietyGUI cellSocietyGUI;

    @Override
    public void start (Stage stage) {
        cellSocietyGUI = new CellSocietyGUI();
        properties = AppResources.getResourceFile(RESOURCE_FILENAME);
        stage.setTitle(properties.getProperty(APP_TITLE_KEY));
        stage.setScene(cellSocietyGUI.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}