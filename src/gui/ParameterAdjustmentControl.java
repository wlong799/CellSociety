// This entire file is part of my masterpiece.
// Will Long

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
 * ParameterAdjustmentControl provides a GUI element that allows for adjusting
 * parameters of the game while it is running. The ComboBox allows the user to
 * select which parameter to edit, while the Slider allows them to then adjust
 * the value of the specified parameter. There is also a Text object below to
 * display the current value of the parameter for easier interaction.
 *
 * In addition to the reasons specified in InputPanel, this class is well
 * designed because the creation of each individual component is separated into
 * its own method. This makes code more flexible to extensions, as adding new
 * elements or editing elements only requires modifying single functions.
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

        createParameterBox(parameters);
        createParameterSlider(targetCellGrid);
        createParameterText();

        getChildren().addAll(parameterComboBox, parameterAdjustmentSlider, currentParameterValue);
    }

    private void createParameterBox(List<GameParameter> parameters) {
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
    }

    private void createParameterSlider(CellGrid targetCellGrid) {
        parameterAdjustmentSlider = new Slider();
        parameterAdjustmentSlider.setPrefWidth(AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource());
        parameterAdjustmentSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentParameterName == null) {
                currentParameterValue.setText(AppResources.PARAMETER_DEFAULT_MESSAGE.getResource());
                return;
            }
            currentParameterValue.setText(newValue.intValue() + " ");
            targetCellGrid.updateParameter(currentParameterName, newValue.intValue());
        });
    }

    private void createParameterText() {
        currentParameterValue = new Text(AppResources.PARAMETER_DEFAULT_MESSAGE.getResource());
        currentParameterValue.setId("parameter-value-text");
        currentParameterValue.setWrappingWidth(AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource());
    }

    public double getControlWidth() {
        return AppResources.PARAMETER_COMBO_BOX_WIDTH.getDoubleResource();
    }
}
