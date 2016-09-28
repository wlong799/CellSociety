package gui;

import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.List;

public class ParameterAdjustmentControl extends VBox {
    private static final double PADDING = 10;

    private ComboBox parameterComboBox;
    private Slider parameterAdjustmentSlider;
    private String currentParameterName;

    public ParameterAdjustmentControl(double xPos, double yPos, double width, double height,
                                      List<GameParameter> parameters, CellGrid targetCellGrid) {
        super(PADDING);
        setLayoutX(xPos);
        setLayoutY(yPos);
        setWidth(width);
        setHeight(height);

        currentParameterName = null;

        parameterComboBox = new ComboBox();
        for (GameParameter param : parameters) {
            parameterComboBox.getItems().add(param.getName());
        }
        parameterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentParameterName = newValue.toString();
            for (GameParameter parameter : parameters) {
                if (parameter.getName().equals(currentParameterName)) {
                    parameterAdjustmentSlider.setMin(parameter.getMinVal());
                    parameterAdjustmentSlider.setMax(parameter.getMaxVal());
                    parameterAdjustmentSlider.setValue(parameter.getCurrentVal());
                    break;
                }
            }
        });

        parameterAdjustmentSlider = new Slider();
        parameterAdjustmentSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentParameterName == null) {
                return;
            }
            targetCellGrid.updateParameter(currentParameterName, newValue.intValue());
        });

        getChildren().addAll(parameterComboBox, parameterAdjustmentSlider);
    }
}
