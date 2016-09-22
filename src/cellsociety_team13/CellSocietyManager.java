package cellsociety_team13;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * @author Will Long
 */
public class CellSocietyManager {
    private Group sceneRoot;
    private Scene scene;
    private int sceneWidth, sceneHeight;

    private CellGrid cellGrid;

    public CellSocietyManager(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
    }

    public Scene getScene() {
        return scene;
    }
}
