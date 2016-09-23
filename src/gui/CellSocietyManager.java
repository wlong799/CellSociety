package gui;

import cellsociety_team13.CellGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import rule.SpreadingOfFire;
import xmlparser.GameInfoReader;

/**
 * @author Will Long
 */
public class CellSocietyManager {
	public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    
    private static final int PADDING = 25;
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 750;
    
    private Group sceneRoot;
    private Scene scene;
    private Timeline runAnimation;

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
        stepButton.setOnAction(e -> step());
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
    
    private void step(){
    	cellGrid.step();
    }
    
    private void run(){
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> cellGrid.step());
    	runAnimation = new Timeline();
    	runAnimation.setCycleCount(Timeline.INDEFINITE);
    	runAnimation.getKeyFrames().add(frame);
    	runAnimation.play();
    }

    public Scene getScene() {
        return scene;
    }

    private void readGameInfoFile(String filename) {
        GameInfoReader gameInfoReader = new GameInfoReader(filename);
    }
}
