package gui;

import java.util.List;

import cellsociety_team13.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.shape.Rectangle;
import rule.*;
import xmlparser.GameInfoReader;

/**
 * Is the main front-end GUI for all of CellSociety. It contains a number of
 * GUI elements that are defined in the remainder of the gui package. It sets
 * their position, and sends them information necessary to interact with the
 * application properly. It also provides the interface between the GUI and the
 * back-end (e.g. sends XML information to CellGrid back-end, updates simulation
 * as buttons/sliders are updated, etc.)
 */
public class CellSocietyGUI {
    private Group sceneRoot;
    private Scene scene;

    private double appWidth, appHeight;

    private GameInfoReader gameInfoReader;
    private Rule rule;
    private TitleScreen titleScreen;
    private TitleBox titleBox;
    private CellGrid cellGrid;
    private CellTypeChart cellTypeChart;
    private InputPanel inputPanel;
    private String typeShape;

    public CellSocietyGUI() {
        sceneRoot = new Group();
        appWidth = AppResources.APP_WIDTH.getDoubleResource();
        appHeight = AppResources.APP_HEIGHT.getDoubleResource();
        scene = new Scene(sceneRoot, appWidth, appHeight);
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

        scene.getStylesheets().add(getClass().getResource(AppResources.APP_CSS.getResource()).toExternalForm());
        sceneRoot.setId("root");

        loadTitleScreen();
    }

    /**
     * Allows user to click on cells and update their types as the game is running.
     *
     * @param x is x location of click.
     * @param y is y location of click.
     */
    private void handleMouseInput(double x, double y) {
        if (sceneRoot.getChildren().contains(titleScreen)) {
            return;
        }
        Rectangle mousePos = new Rectangle(x, y, 1, 1);
        double xOrigin = cellGrid.getLayoutX();
        double yOrigin = cellGrid.getLayoutY();
        for (Cell cell : cellGrid.getCells()) {
            double xTest = xOrigin + cell.getMyCol() * cellGrid.getDrawCellWidth();
            double yTest = yOrigin + cell.getMyRow() * cellGrid.getDrawCellHeight();
            Rectangle test = new Rectangle(xTest, yTest, cellGrid.getDrawCellWidth(), cellGrid.getDrawCellHeight());
            if (mousePos.getBoundsInParent().intersects(test.getBoundsInParent())) {
                String ruleName = gameInfoReader.getRuleClassName();
                if (ruleName.equals("GameOfLife")) {
                    cell.setCurrentType("LIVE");
                } else if (ruleName.equals("SchellingModel")) {
                    cell.setCurrentType("X");
                } else if (ruleName.equals("WatorWorld")) {
                    cell.setCurrentType("SHARK");
                } else if (ruleName.equals("SpreadingOfFire")) {
                    cell.setCurrentType("FIRE");
                } else if (ruleName.equals("ForagingAnts")) {
                    cell.setCurrentType("NEST");
                } else if (ruleName.equals("SlimeMold")) {
                    cell.setCurrentType("TURTLE");
                } else if (ruleName.equals("SugarScape")) {
                    cell.setCurrentType("AGENT");
                }
                rule.setColor(cell, cellGrid);
            }
        }
    }

    /**
     * Loads a new game, using information loaded from an XML file
     * and contained within the GUI's GameInfoReader. Creates the
     * new CellGrid, CellTypeChart, InputPanel, and TitleBox.
     */
    private void loadGame() {
        String gameFilename = titleScreen.getXMLFilename();
        if (gameFilename == null) {
            return;
        }
        if (typeShape == null) typeShape = "square";
        gameFilename = AppResources.APP_DATA.getResource() + gameFilename;
        gameInfoReader = new GameInfoReader(gameFilename);
        sceneRoot.getChildren().clear();
        Rectangle background = new Rectangle(appWidth, appHeight);
        background.setId("main-bg");
        sceneRoot.getChildren().add(background);
        loadRule();
        createTitleBox();
        createCellGrid();
        createCellTypeChart();
        createInputPanel();
    }

    /**
     * Loads the main title screen for selecting a new game.
     */
    private void loadTitleScreen() {
        sceneRoot.getChildren().clear();
        EventHandler<ActionEvent> startButtonHandler = event -> {
            loadGame();
        };
        titleScreen = new TitleScreen(appWidth, appHeight, startButtonHandler);
        sceneRoot.getChildren().add(titleScreen);
    }

    /**
     * Depending on the name of the rule specified in the XML
     * file, loads the correct Rule subclass.
     */
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
        } else if (ruleName.equals("ForagingAnts")) {
            rule = new ForagingAnts();
        } else if (ruleName.equals("SlimeMold")) {
            rule = new SlimeMold();
        } else if (ruleName.equals("SugarScape")) {
            rule = new SugarScape();
        }
    }

    /**
     * Creates the title box located at the top of the GUI
     */
    private void createTitleBox() {
        double height = AppResources.TITLE_BOX_HEIGHT.getDoubleResource();
        String title = gameInfoReader.getTitle();
        titleBox = new TitleBox(appWidth, height, title);
        sceneRoot.getChildren().add(titleBox);
    }


    /**
     * Calculates all the information necessary to send to the CellGrid,
     * and initializes it. Ensures that the Grid is correctly positioned within
     * the main screen (i.e. centered and taking up as much space as possible
     * considering its size).
     */
    private void createCellGrid() {
        double drawHeight = appHeight -
                AppResources.INPUT_PANEL_HEIGHT.getDoubleResource() -
                AppResources.TITLE_BOX_HEIGHT.getDoubleResource() -
                (2 * AppResources.APP_PADDING.getDoubleResource());
        double drawWidth = appWidth - (2 * AppResources.APP_PADDING.getDoubleResource());
        int gridWidth = gameInfoReader.getGridWidth();
        int gridHeight = gameInfoReader.getGridHeight();
        if (drawWidth / gridWidth > drawHeight / gridHeight) {
            drawWidth = drawHeight * gridWidth / gridHeight;
        } else {
            drawHeight = drawWidth * gridHeight / gridWidth;
        }
        double ySpace = appHeight -
                AppResources.INPUT_PANEL_HEIGHT.getDoubleResource() -
                AppResources.TITLE_BOX_HEIGHT.getDoubleResource();

        double xPos = (appWidth / 2) - (drawWidth / 2);
        double yPos = AppResources.TITLE_BOX_HEIGHT.getDoubleResource() + (ySpace / 2) - (drawHeight / 2);
        List<String> initialCellTypes = gameInfoReader.getInitialCellTypeLocations();
        List<GameParameter> initialParameters = gameInfoReader.getGameParameters();
        boolean toroidal = gameInfoReader.isToroidal();

        typeShape = titleScreen.getShapeType();

        if (typeShape.equals("Squares")) {
            cellGrid = new CellGridSquare(xPos, yPos, drawWidth, drawHeight, gridWidth,
                    gridHeight, initialCellTypes, rule, initialParameters, toroidal);
        } else {
            cellGrid = new CellGridHexagon(xPos, yPos, drawWidth, drawHeight, gridWidth,
                    gridHeight, initialCellTypes, rule, initialParameters, toroidal);
        }
        sceneRoot.getChildren().add(cellGrid);
    }

    /**
     * Initializes a new CellTypeChart to show information about the proportions
     * of cell types in the grid over time.
     */
    private void createCellTypeChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis(0, 1, 0.1);

        double drawHeight = appHeight -
                AppResources.INPUT_PANEL_HEIGHT.getDoubleResource() -
                AppResources.TITLE_BOX_HEIGHT.getDoubleResource() -
                (2 * AppResources.APP_PADDING.getDoubleResource());
        double drawWidth = appHeight - (2 * AppResources.APP_PADDING.getDoubleResource());
        double ySpace = appHeight -
                AppResources.INPUT_PANEL_HEIGHT.getDoubleResource() -
                AppResources.TITLE_BOX_HEIGHT.getDoubleResource();
        double xPos = (appWidth / 2) - (drawWidth / 2);
        double yPos = AppResources.TITLE_BOX_HEIGHT.getDoubleResource() + (ySpace / 2) - (drawHeight / 2);

        cellTypeChart = new CellTypeChart(xAxis, yAxis, xPos, yPos, drawWidth, drawHeight);
        cellTypeChart.updateCellData(cellGrid.getCellProportions());
    }

    /**
     * Creates the input panel by passing in the event handlers and
     * other GUI elements necessary for the user to be able to
     * interact with the game.
     */
    private void createInputPanel() {
        EventHandler<ActionEvent> gameSelectHandler = event -> {
            loadTitleScreen();
        };

        EventHandler<ActionEvent> toggleViewHandler = event -> {
            if (sceneRoot.getChildren().contains(cellGrid)) {
                sceneRoot.getChildren().remove(cellGrid);
                sceneRoot.getChildren().add(cellTypeChart);
            } else {
                sceneRoot.getChildren().remove(cellTypeChart);
                sceneRoot.getChildren().add(cellGrid);
            }
        };

        List<GameParameter> params = gameInfoReader.getGameParameters();

        double height = AppResources.INPUT_PANEL_HEIGHT.getDoubleResource();
        inputPanel = new InputPanel(0, appHeight - height, appWidth, height, gameSelectHandler,
                toggleViewHandler, cellGrid, cellTypeChart, params);
        sceneRoot.getChildren().add(inputPanel);
    }

    public Scene getScene() {
        return scene;
    }
}
