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

public class CellGridTriangle extends CellGrid {

	public CellGridTriangle(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight, List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters) {
		super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters);

	}



	@Override
	public List<Cell> getNonDiagNeighbours(Cell myCell) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> getNeighbours(Cell myCell) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Cell getVerticesAndMakeCell(List<String> initialCellTypes, int row, int col, int arrayPos, double cellXPos,
			double cellYPos) {
		// TODO Auto-generated method stub NEED TO CHANGE TO TRIANGLE COORDS.
	//	double[] myVertices = new double[]{cellXPos, cellYPos, cellXPos+drawCellWidth, cellYPos, cellXPos + drawCellWidth, cellYPos+drawCellHeight, cellXPos, cellYPos+drawCellHeight};
	//	Cell cell = new Cell(myVertices, initialCellTypes.get(arrayPos), cellXPos, cellYPos,
	//					     drawCellWidth, drawCellHeight, row, col);
		//return cell;
		return null;
	}



	@Override
	public List<BackgroundCell> getNeighbours(Cell myCell, CellGrid myGrid) {
		// TODO Auto-generated method stub
		return null;
	}
}

