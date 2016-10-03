package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import rule.Rule;

/**
 * CellGrid is responsible for holding the current Cells within the Cell Society
 * game. It stores the position of the cells and uses the specified Rule class
 * to update the Cells when specified.
 */

public class CellGridHexagon extends CellGrid {

    public CellGridHexagon(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight,
                           List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters, boolean toroidal) {
        super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters, toroidal);
    }

    @Override
    public Cell getVerticesAndMakeCell(String cellType, int row, int col, double cellXPos, double cellYPos) {
        if (row % 2 != 0) {
            cellXPos += (drawCellWidth / 2);
        }
        double offset = (drawCellWidth / 2) / Math.sqrt(3);
        double[] vertices = new double[]
                {
                        cellXPos, cellYPos,
                        cellXPos + (drawCellWidth / 2), cellYPos - offset,
                        cellXPos + drawCellWidth, cellYPos,
                        cellXPos + drawCellWidth, cellYPos + drawCellHeight - offset,
                        cellXPos + (drawCellWidth / 2), cellYPos + drawCellHeight,
                        cellXPos, cellYPos + drawCellHeight - offset
                };
        Cell cell = new Cell(vertices, cellType, row, col);
        return cell;
    }

    @Override
    public List<Cell> getNonDiagNeighbours(Cell myCell) {
        return getNeighbours(myCell);
    }

    @Override
    public List<Cell> getNeighbours(Cell myCell) {
        List<Cell> neighbours = new ArrayList<>();
        int row = myCell.getMyRow();
        int col = myCell.getMyCol();
        int shift = (row % 2);

        neighbours.add(getCell(row - 1, col - 1 + shift));
        neighbours.add(getCell(row - 1, col + shift));
        neighbours.add(getCell(row, col - 1));
        neighbours.add(getCell(row, col + 1));
        neighbours.add(getCell(row + 1, col - 1 + shift));
        neighbours.add(getCell(row + 1, col + shift));

        List<Cell> result = new ArrayList<>();
        for (Cell neighbour : neighbours) {
            if (neighbour != null) {
                result.add(neighbour);
            }
        }
        return result;
    }

    @Override
    public List<BackgroundCell> getNeighbours(Cell myCell, CellGrid myGrid) {
        return null;
    }
}

