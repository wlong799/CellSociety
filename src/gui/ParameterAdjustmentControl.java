package gui;

import cellsociety_team13.AppResources;
import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Provides a GUI element for adjusting parameters of the game
 * while it is running. The ComboBox allows the user to select which
 * game parameter to edit, while the Slider allows them to then adjust
 * the parameter. The text below the slider shows the current value
 * of the specified parameter.
 */
public class ParameterAdjustmentControl extends VBox {
    private ComboBox<String> parameterComboBox;
    private Slider parameterAdjustmentSlider;
    private Text currentParameterValue;
    private String currentParameterName;

    public ParameterAdjustmentControl(double height, List<GameParameter> parameters, CellGrid targetCellGrid) {
        super(AppResources.INPUT_PANEL_PADDING.getDoubleResource());
        setAlignment(Pos.CENTER);
        setPrefHeight(height);

        currentParameterName = null;

        parameterComboBox = new ComboBox();
        parameterComboBox.setPrefWidth(AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource());
        for (GameParameter param : parameters) {
            parameterComboBox.getItems().add(param.getName());
        }
        parameterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentParameterName = newValue;
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
        parameterAdjustmentSlider.setPrefWidth(AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource());
        parameterAdjustmentSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentParameterName == null) {
                currentParameterValue.setText(AppResources.PARAMETER_DEFAULT_MESSAGE.getResource());
                return;
            }
            currentParameterValue.setText(newValue.intValue()+ " ");
            targetCellGrid.updateParameter(currentParameterName, newValue.intValue());
        });

        currentParameterValue = new Text(AppResources.PARAMETER_DEFAULT_MESSAGE.getResource());
        currentParameterValue.setId("parameter-value-text");
        currentParameterValue.setWrappingWidth(AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource());

        getChildren().addAll(parameterComboBox, parameterAdjustmentSlider, currentParameterValue);
    }
}
