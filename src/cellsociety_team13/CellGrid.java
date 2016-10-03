package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import rule.Rule;

/**
 * CellGrid is responsible for holding the current Cells within the Cell Society
 * game. It stores the position of the cells and uses the specified Rule class
 * to update the Cells when specified.
 */

public abstract class CellGrid extends Group {
    protected double drawWidth, drawHeight;
    protected double drawCellWidth, drawCellHeight;

    protected int gridWidth, gridHeight;

    protected List<Cell> cells = new ArrayList<>();
    protected List<BackgroundCell> bgCells = new ArrayList<>();
    protected Rule rule;

    public CellGrid(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight,
                    List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters) {
        setLayoutX(xPos);
        setLayoutY(yPos);
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        drawCellWidth = drawWidth / gridWidth;
        drawCellHeight = drawHeight / gridHeight;

        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        addItemsToGrid(gridWidth, gridHeight, initialCellTypes);
        this.rule = rule;
        rule.initialize(this, initialParameters);
    }

    public void addItemsToGrid(int gridWidth, int gridHeight, List<String> initialCellTypes) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                int arrayPos = row * gridWidth + col;
                double cellXPos = col * drawCellWidth;
                double cellYPos = row * drawCellHeight;
                Cell cell = getVerticesAndMakeCell(initialCellTypes, row, col, arrayPos, cellXPos, cellYPos);
                cells.add(cell);
                getChildren().add(cell);
                BackgroundCell bgCell = new BackgroundCell(row, col);
                bgCells.add(bgCell);
            }
        }
    }

    public abstract Cell getVerticesAndMakeCell(List<String> initialCellTypes, int row, int col, int arrayPos, double cellXPos,
                                                double cellYPos);

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public List<Cell> getCellsByType(String cellType) {
        List<Cell> myCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getCurrentType().equals(cellType)) {
                myCells.add(cell);
            }
        }
        return myCells;
    }

    public Cell getCell(int row, int col) {
        if ((col >= gridWidth || (col < 0)) || (row >= gridHeight) || (row < 0)) {
            return null;
        } else {
            int arrayPos = row * gridWidth + col;
            return cells.get(arrayPos);
        }
    }

    public BackgroundCell getBGCell(int row, int col) {
        if ((col >= gridWidth || (col < 0)) || (row >= gridHeight) || (row < 0)) {
            return null;
        } else {
            int arrayPos = row * gridWidth + col;
            return bgCells.get(arrayPos);
        }
    }

    public void step() {
        rule.evaluateGrid(this);
        stepToNextStatesAndTypes();
    }

    private void stepToNextStatesAndTypes() {
        for (Cell cell : cells) {
            cell.stepToNextStateAndType();
            this.getBGCellofCell(cell).stepToNextBGStateAndType();
            rule.setColor(cell, this);
        }
    }

    abstract public List<Cell> getNonDiagNeighbours(Cell myCell);

    abstract public List<BackgroundCell> getNeighbours(Cell myCell, CellGrid myGrid);

    abstract public List<Cell> getNeighbours(Cell myCell);

    public void updateParameter(String param, int value) {
        rule.setParameter(param, value);
    }

    public BackgroundCell getBGCellofCell(Cell myCell) {
        return bgCells.get(cells.indexOf(myCell));
    }

    public Cell getCellofBG(BackgroundCell myBGCell) {
        return cells.get(bgCells.indexOf(myBGCell));
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<BackgroundCell> getBgCells() {
        return bgCells;
    }
}
