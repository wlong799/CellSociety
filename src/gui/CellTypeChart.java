package gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Uses the LineChart class to keep a running graph of the proportions of
 * various Cell types in the grid over time. Current cell proportion (from 0 to 1)
 * is on y-axis, while current generation is on x-axis. x-axis continuously scales to
 * appropriately fit all generations.
 */
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

    /**
     * Updates the chart using information about proportions of Cells. Records
     * the proportion of cells for the current generation, and then increments
     * the generation number.
     * @param cellProportions is a Map from String Cell types, to Double proportions
     *                        of the specified Cell type in the grid this generation.
     */
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
