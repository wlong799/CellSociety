package gui;

import cellsociety_team13.CellGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rule.SpreadingOfFire;
import xmlparser.GameInfoReader;

/**
 * @author Will Long
 */
public class CellSocietyManager {
    private static final int PADDING = 25;
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 750;

    private Group sceneRoot;
    private Scene scene;

    private CellGrid cellGrid;

    private TextField xmlFilenameField;
    private Button submitFileButton;

    private Button stepButton, runButton;

    ComboBox parameterAdjustmentList;

    public CellSocietyManager() {
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, SCENE_WIDTH, SCENE_HEIGHT);

        // Just Hard-Coded numbers for test
        cellGrid = new CellGrid(10, 10, 20, 10, 25, 25, new SpreadingOfFire(), sceneRoot);
        // set positioning

        xmlFilenameField = new TextField();
        // set size
        // set position

        submitFileButton = new Button("SUBMIT");
        submitFileButton.setOnAction(e ->
                readGameInfoFile(xmlFilenameField.getCharacters().toString()));

        stepButton = new Button("STEP");
        stepButton.setOnAction(e -> cellGrid.step());
        runButton = new Button("RUN");
        runButton.setOnAction(e -> {
			try {
				cellGrid.run();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});

        parameterAdjustmentList = new ComboBox();
    }

    public Scene getScene() {
        return scene;
    }

    private void readGameInfoFile(String filename) {
        GameInfoReader gameInfoReader = new GameInfoReader(filename);
    }
}
