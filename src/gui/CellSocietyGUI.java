package gui;

import java.util.List;

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
import rule.SchellingModel;
import rule.SpreadingOfFire;
import rule.WatorWorld;
import xmlparser.GameInfoReader;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import rule.Rule;

/**
 * @author Will Long
 */  


public class CellSocietyGUI {
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 750;
    private static final double TEXT_BOX_HEIGHT = 100;
    private static final double PADDING = 25;
    private static final String DEFAULT_XML_FILE = "data/fire.xml";
    private static final double RUN_SPEED_MILLI = 1000;
    
    private Group sceneRoot;
    private Scene scene;
    private Timeline runAnimation;

    private GameInfoReader gameInfoReader;

    private Text title;

    private CellGrid cellGrid;
    private Rule rule;

    private TextField xmlFilenameField;
    private Button submitFileButton;
    private Button stepButton, runButton;
    private ComboBox parameterAdjustmentList;

    public CellSocietyGUI() {
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, SCENE_WIDTH, SCENE_HEIGHT);
        gameInfoReader = new GameInfoReader(DEFAULT_XML_FILE);

        title = new Text(PADDING, PADDING, gameInfoReader.getTitle());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrappingWidth(SCENE_WIDTH - 2*PADDING);
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 36));

        rule = loadRule();
        cellGrid = loadCellGrid();

        xmlFilenameField = new TextField();
        // set size
        // set position

        submitFileButton = new Button("SUBMIT");
        submitFileButton.setOnAction(e ->
                readGameInfoFile(xmlFilenameField.getCharacters().toString()));

        stepButton = new Button("STEP");
        stepButton.setOnAction(e -> step());
        runButton = new Button("RUN");
        runButton.setOnAction(e -> run());

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

    private Rule loadRule() {
        String ruleName = gameInfoReader.getRuleClassName();

        if (ruleName.equals("SchellingModel")) {
            return new SchellingModel();
        } else if (ruleName.equals("WatorWorld")) {
            return new WatorWorld();
        } else if (ruleName.equals("SpreadingOfFire")) {
            return new SpreadingOfFire();
        }

        return null;
    }

    private CellGrid loadCellGrid() {
        double xPos = PADDING;
        double yPos = PADDING * 2 + TEXT_BOX_HEIGHT;
        double drawWidth = SCENE_WIDTH - PADDING * 2;
        double drawHeight = drawWidth;
        int gridWidth = gameInfoReader.getGridWidth();
        int gridHeight = gameInfoReader.getGridHeight();
        List<String> initialCellTypes = gameInfoReader.getInitialCellTypes();
        return new CellGrid(xPos, yPos, drawWidth, drawHeight, gridWidth,
                            gridHeight, initialCellTypes, rule);
    }

    public Scene getScene() {
        return scene;
    }
}
