package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import rule.Rule;

public class CellGridSquare extends CellGrid {

	
	public CellGridSquare(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight,
			List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters) {
		super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters);
		// TODO Auto-generated constructor stub
	}

	public void addItemsToGrid(int gridWidth, int gridHeight, List<String> initialCellTypes) {
		for (int row = 0; row < gridHeight; row++){
 			for (int col = 0; col < gridWidth; col++){
 				int arrayPos = row*gridWidth + col;
				double cellXPos = row * drawCellWidth;
				double cellYPos = col * drawCellHeight;
 				Cell cell = new Cell(initialCellTypes.get(arrayPos), cellXPos, cellYPos,
								     drawCellWidth, drawCellHeight, row, col);
 				cells.add(cell);
 				getChildren().add(cell);
 			}
 		}
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
	public List<BackgroundCell> getNeighbours(BackgroundCell myBackgroundCell) {
		// TODO Auto-generated method stub

		return null;
	}

	
	

}
