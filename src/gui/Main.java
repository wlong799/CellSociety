package gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for CellSociety. Basic boilerplate code for launching the JavaFX
 * application.
 *
 * @author Will Long
 */
public class Main extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 900;

    private CellSocietyManager cellSocietyManager;

    @Override
    public void start (Stage stage) {
        cellSocietyManager = new CellSocietyManager();
        stage.setTitle("Cell Society");
        stage.setScene(cellSocietyManager.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}