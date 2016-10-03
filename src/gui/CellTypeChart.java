package gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

public class CellTypeChart extends LineChart{
    private int currentGeneration;
    private Map<String, XYChart.Series> cellSeriesMap;

    public CellTypeChart(Axis xAxis, Axis yAxis, double xPos, double yPos, double width, double height) {
        super(xAxis, yAxis);
        setLayoutX(xPos);
        setLayoutY(yPos);
        setPrefWidth(width);
        setPrefHeight(height);

        currentGeneration = 0;
        cellSeriesMap = new HashMap<>();
    }

    public void updateCellData(Map<String, Double> cellProportions) {
        for (String cellType : cellProportions.keySet()) {
            if (!cellSeriesMap.containsKey(cellType)) {
                XYChart.Series series = new XYChart.Series();
                series.setName(cellType);
                cellSeriesMap.put(cellType, series);
                getData().add(series);
            }
            XYChart.Series series = cellSeriesMap.get(cellType);
            double proportion = cellProportions.get(cellType);
            series.getData().add(new XYChart.Data(currentGeneration, proportion));
        }
        currentGeneration++;
    }
}
