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

public class TitleScreen extends Group {
    private ImageView backgroundImage;
    private Button startButton;
    private ComboBox<String> fileSelect;
    private String selectedFilename;

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

        getChildren().addAll(backgroundImage, startButton, fileSelect);
    }

    public String getXMLFilename() {
        return selectedFilename;
    }
}
