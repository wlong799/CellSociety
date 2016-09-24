package gui;

import java.util.List;

import cellsociety_team13.CellGrid;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.scene.paint.Color;
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

    Group inputPanel;
    private TextField xmlFilenameField;
    private Button submitFileButton;
    private Button stepButton, runButton;
    private ComboBox parameterAdjustmentList;

    public CellSocietyGUI() {
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.DARKGRAY);

        gameInfoReader = new GameInfoReader(DEFAULT_XML_FILE);

        title = new Text(PADDING, PADDING, gameInfoReader.getTitle());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrappingWidth(SCENE_WIDTH - 2 * PADDING);
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 36));

        loadRule();
        loadCellGrid();
        createInputPanel();

        sceneRoot.getChildren().addAll(title, cellGrid, inputPanel);
    }

    private void step() {
        cellGrid.step();
    }

    private void toggleRunning() {
        if (runAnimation.getStatus().equals(Animation.Status.RUNNING)) {
            runAnimation.pause();
            runButton.setText("RUN");
        } else {
            runAnimation.play();
            runButton.setText("PAUSE");
        }
    }

    private void loadRule() {
        String ruleName = gameInfoReader.getRuleClassName();

        if (ruleName.equals("SchellingModel")) {
            rule = new SchellingModel();
        } else if (ruleName.equals("WatorWorld")) {
            rule = new WatorWorld();
        } else if (ruleName.equals("SpreadingOfFire")) {
            rule = new SpreadingOfFire();
        }
    }

    private void loadCellGrid() {
        double xPos = PADDING;
        double yPos = PADDING * 2 + TEXT_BOX_HEIGHT;
        double drawWidth = SCENE_WIDTH - PADDING * 2;
        double drawHeight = drawWidth;
        int gridWidth = gameInfoReader.getGridWidth();
        int gridHeight = gameInfoReader.getGridHeight();
        List<String> initialCellTypes = gameInfoReader.getInitialCellTypes();
        cellGrid = new CellGrid(xPos, yPos, drawWidth, drawHeight, gridWidth,
                gridHeight, initialCellTypes, rule);
    }

    private void createInputPanel() {
        inputPanel = new Group();
        inputPanel.setLayoutX(PADDING);
        inputPanel.setLayoutY(cellGrid.getBoundsInLocal().getMaxY() + PADDING);

        xmlFilenameField = new TextField("XML GAME FILE");
        xmlFilenameField.setMaxWidth(100);

        submitFileButton = new Button("SUBMIT");
        submitFileButton.setLayoutX(100 + PADDING);
        submitFileButton.setMaxWidth(50);
        submitFileButton.setOnAction(e -> {
            String filename = xmlFilenameField.getCharacters().toString();
            gameInfoReader = new GameInfoReader(filename);
            loadRule();
            loadCellGrid();
                });

        stepButton = new Button("STEP");
        stepButton.setLayoutX(150 + 2*PADDING);
        stepButton.setMaxWidth(50);
        stepButton.setOnAction(e -> step());

        KeyFrame frame = new KeyFrame(Duration.millis(RUN_SPEED_MILLI),
                                      e -> cellGrid.step());
        runAnimation = new Timeline();
        runAnimation.setCycleCount(Timeline.INDEFINITE);
        runAnimation.getKeyFrames().add(frame);

        runButton = new Button("RUN");
        runButton.setLayoutX(200 + 3*PADDING);
        runButton.setMaxWidth(50);
        runButton.setOnAction(e -> toggleRunning());

        parameterAdjustmentList = new ComboBox();
        parameterAdjustmentList.setLayoutX(250 + 4*PADDING);
        parameterAdjustmentList.setMaxWidth(SCENE_WIDTH - 250 - 5*PADDING);
        for (String param : gameInfoReader.getParameterMap().keySet()) {
            parameterAdjustmentList.getItems().add(param);
        }

        inputPanel.getChildren().addAll(xmlFilenameField, submitFileButton,
                                        stepButton, runButton, parameterAdjustmentList);
    }

    public Scene getScene() {
        return scene;
    }
}
