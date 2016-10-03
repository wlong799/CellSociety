package gui;

import cellsociety_team13.AppResources;
import cellsociety_team13.CellGrid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Map;

public class AnimationControl extends VBox {
    private static final double DEFAULT_RUN_SPEED_MILLI = 100;
    private static final double MIN_RATE = 0.25;
    private static final double MAX_RATE = 2.5;

    private Timeline runAnimation;
    private CellGrid targetCellGrid;
    private CellTypeChart targetChart;

    private HBox buttonBox;
    private Button stepButton, runButton;
    private Slider runSpeedSlider;

    public AnimationControl(double height, CellGrid cellGrid, CellTypeChart cellTypeChart) {
        super(AppResources.INPUT_PANEL_PADDING.getDoubleResource());
        setPrefHeight(height);
        setAlignment(Pos.CENTER);
        targetCellGrid = cellGrid;
        targetChart = cellTypeChart;

        stepButton = new Button(AppResources.STEP_TITLE.getResource());
        stepButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        stepButton.setOnAction(e -> step());

        KeyFrame frame = new KeyFrame(Duration.millis(AppResources.ANIMATION_SPEED.getDoubleResource()),
                e -> step());
        runAnimation = new Timeline();
        runAnimation.setCycleCount(Timeline.INDEFINITE);
        runAnimation.getKeyFrames().add(frame);

        runButton = new Button(AppResources.RUN_TITLE.getResource());
        runButton.setPrefWidth(AppResources.INPUT_BUTTON_WIDTH.getDoubleResource());
        runButton.setOnAction(e -> toggleRunning());

        double minRate = AppResources.ANIMATION_MIN_RATE.getDoubleResource();
        double maxRate = AppResources.ANIMATION_MAX_RATE.getDoubleResource();
        double defaultRate = 1;
        runSpeedSlider = new Slider(minRate, maxRate, defaultRate);
        runSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                runAnimation.setRate(newValue.doubleValue());
                });
        double sliderWidth = (2 * AppResources.INPUT_BUTTON_WIDTH.getDoubleResource()) +
                AppResources.INPUT_PANEL_PADDING.getDoubleResource();
        runSpeedSlider.setPrefWidth(sliderWidth);

        buttonBox = new HBox(AppResources.INPUT_PANEL_PADDING.getDoubleResource());
        buttonBox.getChildren().addAll(stepButton, runButton);
        
        getChildren().addAll(buttonBox, runSpeedSlider);
    }

    private void step() {
        targetCellGrid.step();
        targetChart.updateCellData(targetCellGrid.getCellProportions());
    }

    private void toggleRunning() {
        if (runAnimation.getStatus().equals(Animation.Status.RUNNING)) {
            runAnimation.pause();
            runButton.setText(AppResources.RUN_TITLE.getResource());
        } else {
            runAnimation.play();
            runButton.setText(AppResources.PAUSE_TITLE.getResource());
        }
    }


}
