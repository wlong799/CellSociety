package gui;

import cellsociety_team13.AppResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

/**
 * Initial TitleScreen providing introduction to CellSociety game.
 * Provides a background image, as well as a ComboBox to select the
 * XML file to load, a button for starting the game, and a ComboBox
 * for selecting the tiling. Whenever a new game is selected from the
 * main GUI, the application returns to this screen.
 */
public class TitleScreen extends Group {
    private ImageView backgroundImage;
    private Button startButton;
    private ComboBox<String> fileSelect;
    private String selectedFilename;
    private String selectShape; 
    private ComboBox<String> shapeSelect;

    public TitleScreen(double width, double height, EventHandler<ActionEvent> startButtonHandler) {
        backgroundImage = new ImageView();
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        String filename = AppResources.TITLE_IMAGE_LOCATION.getResource();
        try {
            InputStream istream = new FileInputStream(new File(filename));
            Image image = new Image(istream);
            backgroundImage.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(AppResources.TITLE_IMAGE_ERROR.getResource() + filename);
        }

        startButton = new Button("Start Game");
        startButton.setOnAction(startButtonHandler);
        startButton.setPrefWidth(AppResources.TITLE_BUTTON_WIDTH.getDoubleResource());
        double buttonX = (width / 2) - (startButton.getPrefWidth() / 2);
        double buttonY = height - AppResources.TITLE_BUTTON_OFFSET.getDoubleResource();
        startButton.setLayoutX(buttonX);
        startButton.setLayoutY(buttonY);

        fileSelect = new ComboBox<>();
        fileSelect.setPrefWidth(AppResources.TITLE_FILESELECT_WIDTH.getDoubleResource());
        double fileSelectX = (width / 2) - (fileSelect.getPrefWidth() / 2);
        double fileSelectY = height - AppResources.TITLE_FILESELECT_OFFSET.getDoubleResource();
        fileSelect.setLayoutX(fileSelectX);
        fileSelect.setLayoutY(fileSelectY);
        File dataDirectory = new File(AppResources.APP_DATA.getResource());
        File[] dataFiles = dataDirectory.listFiles();
        for (File file : dataFiles) {
            fileSelect.getItems().add(file.getName());
        }


        selectedFilename = null;
        fileSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
                    selectedFilename = newValue;
                });
        
        shapeSelect = new ComboBox<>();
        shapeSelect.setPrefWidth(AppResources.TITLE_FILESELECT_WIDTH.getDoubleResource());
        shapeSelect.setLayoutX(fileSelectX);
        shapeSelect.setLayoutY(fileSelectY+50);
        shapeSelect.getItems().add("Hexagons");
        shapeSelect.getItems().add("Squares");
        
        selectShape = null; 
        shapeSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
        	selectShape = newValue;
        });
        
        getChildren().addAll(backgroundImage, startButton, fileSelect, shapeSelect);
    }

    /**
     * Returns name of XML file to use for loading the game.
     * @return XML file selected from ComboBox.
     */
    public String getXMLFilename() {
        return selectedFilename;
    }

    /**
     * Returns tiling method selected for the game (e.g. Hexagon).
     * @return String specifying tiling method.
     */
    public String getShapeType() {
        return selectShape;
    }
}
