package gui;

import java.util.List;

import cellsociety_team13.CellGrid;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.paint.Color;
import rule.*;
import xmlparser.GameInfoReader;

public class CellSocietyGUI {
    private static final double TITLE_BOX_HEIGHT = 100;
    private static final double CELL_GRID_SIZE = 500;
    private static final double INPUT_PANEL_HEIGHT = 100;
    private static final double PADDING = 25;

    private static final double SCENE_WIDTH = CELL_GRID_SIZE + (2 * PADDING);
    private static final double SCENE_HEIGHT = TITLE_BOX_HEIGHT + CELL_GRID_SIZE +
            INPUT_PANEL_HEIGHT + (4 * PADDING);

    private static final Color BACKGROUND_COLOR = Color.DARKBLUE;
    private static final String DEFAULT_XML_FILE = "data/fire.xml";

    private Group sceneRoot;
    private Scene scene;

    private GameInfoReader gameInfoReader;
    private Rule rule;
    private TitleBox titleBox;
    private CellGrid cellGrid;
    private InputPanel inputPanel;

    public CellSocietyGUI() {
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(BACKGROUND_COLOR);

        gameInfoReader = new GameInfoReader(DEFAULT_XML_FILE);
        loadRule();

        createTitleBox();
        createCellGrid();
        createInputPanel();
    }

    public Scene getScene() {
        return scene;
    }

    private void loadRule() {
        String ruleName = gameInfoReader.getRuleClassName();
        if (ruleName.equals("GameOfLife")) {
            rule = new GameOfLife();
        } else if (ruleName.equals("SchellingModel")) {
            rule = new SchellingModel();
        } else if (ruleName.equals("WatorWorld")) {
            rule = new WatorWorld();
        } else if (ruleName.equals("SpreadingOfFire")) {
            rule = new SpreadingOfFire();
        }
    }

    private void createTitleBox() {
        double x = PADDING;
        double y = PADDING;
        double width = SCENE_WIDTH - (2 * PADDING);
        double height = TITLE_BOX_HEIGHT;
        String title = gameInfoReader.getTitle();
        titleBox = new TitleBox(x, y, width, height, title);
        sceneRoot.getChildren().add(titleBox);
    }


    private void createCellGrid() {
        double xPos = PADDING;
        double yPos = PADDING * 2 + TITLE_BOX_HEIGHT;
        double drawWidth = SCENE_WIDTH - PADDING * 2;
        double drawHeight = drawWidth;
        int gridWidth = gameInfoReader.getGridWidth();
        int gridHeight = gameInfoReader.getGridHeight();
        List<String> initialCellTypes = gameInfoReader.getInitialCellTypes();
        cellGrid = new CellGrid(xPos, yPos, drawWidth, drawHeight, gridWidth,
                gridHeight, initialCellTypes, rule);
        sceneRoot.getChildren().add(cellGrid);
    }

    private void createInputPanel() {
        double x = PADDING;
        double y = cellGrid.getBoundsInParent().getMaxY() + PADDING;
        double width = SCENE_WIDTH - (PADDING * 2);
        double height = INPUT_PANEL_HEIGHT;

        EventHandler<ActionEvent> submitFileHandler = event -> {
            String filename = inputPanel.getXMLFilename();
            gameInfoReader = new GameInfoReader(filename);
            loadRule();
            createTitleBox();
            createCellGrid();
            createInputPanel();
        };

        String[] params = gameInfoReader.getParameterMap().keySet().toArray(new String[0]);

        inputPanel = new InputPanel(x, y, width, height, submitFileHandler, cellGrid, params);
        sceneRoot.getChildren().add(inputPanel);
    }
}
