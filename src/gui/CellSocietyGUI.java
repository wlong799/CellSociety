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

    private void handleMouseInput(double x, double y) {
        if (sceneRoot.getChildren().contains(titleScreen)) {
            return;
        }
    	Rectangle mousePos = new Rectangle(x, y, 1, 1);
    	double xOrigin = cellGrid.getLayoutX();
    	double yOrigin = cellGrid.getLayoutY();
		for (Cell cell : cellGrid.getCells()){
			double xTest = xOrigin + cell.getMyCol()*cellGrid.getDrawCellWidth();
			double yTest = yOrigin + cell.getMyRow()*cellGrid.getDrawCellHeight();
			Rectangle test = new Rectangle(xTest, yTest, cellGrid.getDrawCellWidth(), cellGrid.getDrawCellHeight());
			if (mousePos.getBoundsInParent().intersects(test.getBoundsInParent())){
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

	private void loadGame() {
        String gameFilename = titleScreen.getXMLFilename();
        if (gameFilename == null) {
            return;
        }
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

    private void loadTitleScreen() {
        sceneRoot.getChildren().clear();
        EventHandler<ActionEvent> startButtonHandler = event -> {
            loadGame();
        };
        titleScreen = new TitleScreen(appWidth, appHeight, startButtonHandler);
        sceneRoot.getChildren().add(titleScreen);
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
        } else if (ruleName.equals("ForagingAnts")) {
            rule = new ForagingAnts();
        } else if (ruleName.equals("SlimeMold")) {
            rule = new SlimeMold();
        } else if (ruleName.equals("SugarScape")) {
            rule = new SugarScape();
        }
    }

    private void createTitleBox() {
        double height = AppResources.TITLE_BOX_HEIGHT.getDoubleResource();
        String title = gameInfoReader.getTitle();
        titleBox = new TitleBox(appWidth, height, title);
        sceneRoot.getChildren().add(titleBox);
    }


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

        if (gameInfoReader.getGridTiling().equals(AppResources.XML_TILING_SQUARE.getResource())) {
            cellGrid = new CellGridSquare(xPos, yPos, drawWidth, drawHeight, gridWidth,
                    gridHeight, initialCellTypes, rule, initialParameters, toroidal);
        } else {
            cellGrid = new CellGridHexagon(xPos, yPos, drawWidth, drawHeight, gridWidth,
                gridHeight, initialCellTypes, rule, initialParameters, toroidal);
        }

        sceneRoot.getChildren().add(cellGrid);
    }

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
