package gui;

import cellsociety_team13.CellGrid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AnimationControl extends VBox {
    private static final double PADDING = 25;

    private static final double DEFAULT_RUN_SPEED_MILLI = 1000;
    private static final double MIN_RATE = 0.25;
    private static final double MAX_RATE = 2.5;

    private Timeline runAnimation;
    private CellGrid targetCellGrid;

    private Button stepButton, runButton;
    private Slider runSpeedSlider;

    public AnimationControl(double xPos, double yPos, double width, double height, CellGrid cellGrid) {
        super(PADDING);
        setLayoutX(xPos);
        setLayoutY(yPos);
        setWidth(width);
        setHeight(height);
        setAlignment(Pos.CENTER);
        targetCellGrid = cellGrid;

        stepButton = new Button("STEP");
        stepButton.setOnAction(e -> targetCellGrid.step());

        KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_RUN_SPEED_MILLI),
                e -> step());
        runAnimation = new Timeline();
        runAnimation.setCycleCount(Timeline.INDEFINITE);
        runAnimation.getKeyFrames().add(frame);

        runButton = new Button("RUN");
        runButton.setOnAction(e -> toggleRunning());

        runSpeedSlider = new Slider(MIN_RATE, MAX_RATE, 1);
        runSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                runAnimation.setRate(newValue.doubleValue());
                });

        getChildren().addAll(stepButton, runButton, runSpeedSlider);
    }

    private void step() {
        targetCellGrid.step();
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


}
