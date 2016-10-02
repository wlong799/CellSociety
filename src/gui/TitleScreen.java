package gui;

import cellsociety_team13.AppResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

public class TitleScreen extends Group {
    private ImageView backgroundImage;
    private Button startButton;

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
        getChildren().addAll(backgroundImage, startButton);
    }

}
