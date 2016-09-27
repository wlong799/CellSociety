package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class XMLFileControl extends VBox {
    private static final double PADDING = 25;

    private TextField xmlFilenameField;
    private Button submitFileButton;

    public XMLFileControl(double xPos, double yPos, double width, double height,
                          EventHandler<ActionEvent> submitFileHandler) {
        super(PADDING);
        setLayoutX(xPos);
        setLayoutY(yPos);
        setWidth(width);
        setHeight(height);
        setAlignment(Pos.CENTER);

        xmlFilenameField = new TextField("XML GAME FILE");

        submitFileButton = new Button("SUBMIT");
        submitFileButton.setOnAction(submitFileHandler);

        getChildren().addAll(xmlFilenameField, submitFileButton);
    }

    public String getFilename() {
        String filename = xmlFilenameField.getCharacters().toString();
        if(!filename.substring(filename.length()-4, filename.length()).equals(".xml")){
            filename =  filename + ".xml";
        }
        filename = "data/" + filename;
        return filename;
    }
}
