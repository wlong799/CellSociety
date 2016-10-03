package gui;

import javafx.beans.NamedArg;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;

public class CellTypeChart extends LineChart{
    public CellTypeChart(Axis xAxis, Axis yAxis, double xPos, double yPos, double width, double height) {
        super(xAxis, yAxis);
        setLayoutX(xPos);
        setLayoutY(yPos);
        setPrefWidth(width);
        setPrefHeight(height);
    }
}
