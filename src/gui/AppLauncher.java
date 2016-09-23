package gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * AppLauncher class for CellSociety. Basic boilerplate code for launching the JavaFX
 * application.
 *
 * @author Will Long
 */
public class AppLauncher extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 900;

    private CellSocietyGUI cellSocietyGUI;

    @Override
    public void start (Stage stage) {
        cellSocietyGUI = new CellSocietyGUI();
        stage.setTitle("Cell Society");
        stage.setScene(cellSocietyGUI.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}