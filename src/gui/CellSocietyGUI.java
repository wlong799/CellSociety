package gui;

import cellsociety_team13.CellGrid;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import rule.Rule;
import rule.SpreadingOfFire;
import xmlparser.GameInfoReader;

public class CellSocietyGUI {
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 750;
    private static final double PADDING = 25;
    private static final String DEFAULT_XML_FILE = "data/fire.xml";

    private Group sceneRoot;
    private Scene scene;

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

        // ???????????
        //rule = Class.forName(gameInfoReader.getRuleClassName().getConstructor(St))











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
