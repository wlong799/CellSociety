package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import rule.Rule;

public class CellGridSquare extends CellGrid {


    public CellGridSquare(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight,
                          List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters, boolean toroidal) {
        super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters, toroidal);
    }


    public Cell getVerticesAndMakeCell(String cellType, int row, int col, double cellXPos, double cellYPos) {
        double[] myVertices = new double[]{cellXPos, cellYPos,
                cellXPos + drawCellWidth, cellYPos,
                cellXPos + drawCellWidth, cellYPos + drawCellHeight,
                cellXPos, cellYPos + drawCellHeight};
        Cell cell = new Cell(myVertices, cellType, row, col);
        return cell;
    }

    public List<Cell> getNonDiagNeighbours(Cell myCell) {
        List<Cell> nonDiagNeighbours = new ArrayList<Cell>();
        if (getCell(myCell.getMyRow() + 1, myCell.getMyCol()) != null) {
            nonDiagNeighbours.add(getCell(myCell.getMyRow() + 1, myCell.getMyCol()));
        }
        if (getCell(myCell.getMyRow() - 1, myCell.getMyCol()) != null) {
            nonDiagNeighbours.add(getCell(myCell.getMyRow() - 1, myCell.getMyCol()));
        }
        if (getCell(myCell.getMyRow(), myCell.getMyCol() + 1) != null) {
            nonDiagNeighbours.add(getCell(myCell.getMyRow(), myCell.getMyCol() + 1));
        }
        if (getCell(myCell.getMyRow(), myCell.getMyCol() - 1) != null) {
            nonDiagNeighbours.add(getCell(myCell.getMyRow(), myCell.getMyCol() - 1));
        }
        return nonDiagNeighbours;
    }

    public List<Cell> getNeighbours(Cell myCell) {
        List<Cell> myNeighbours = new ArrayList<Cell>(getNonDiagNeighbours(myCell));
        if (getCell(myCell.getMyRow() + 1, myCell.getMyCol() + 1) != null) {
            myNeighbours.add(getCell(myCell.getMyRow() + 1, myCell.getMyCol() + 1));
        }
        if (getCell(myCell.getMyRow() + 1, myCell.getMyCol() - 1) != null) {
            myNeighbours.add(getCell(myCell.getMyRow() + 1, myCell.getMyCol() - 1));
        }
        if (getCell(myCell.getMyRow() - 1, myCell.getMyCol() - 1) != null) {
            myNeighbours.add(getCell(myCell.getMyRow() - 1, myCell.getMyCol() - 1));
        }
        if (getCell(myCell.getMyRow() - 1, myCell.getMyCol() + 1) != null) {
            myNeighbours.add(getCell(myCell.getMyRow() - 1, myCell.getMyCol() + 1));
        }
        return myNeighbours;
    }

    @Override
    public List<BackgroundCell> getNeighbours(Cell myCell, CellGrid myGrid) {
        List<BackgroundCell> myBGNeighbours = new ArrayList<BackgroundCell>();
        List<Cell> myNeighbours = new ArrayList<Cell>(getNonDiagNeighbours(myCell));
        for (Cell myNeighbour : myNeighbours) {
            myBGNeighbours.add(myGrid.getBGCellofCell(myNeighbour));
        }
        return myBGNeighbours;
    }


}
