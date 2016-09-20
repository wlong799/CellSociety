package cellsociety_team13;

import java.util.ArrayList;

public class SchellingModel extends Rule {

	void evaluateCell(Cell myCell, CellGrid myGrid) {
		if (!satisfiedCell(myCell, myGrid)) {
			myCell.setNextState = ;
			findAnEmptyValidCell(myCell, myGrid).setNextState = "EMPTY";
		}
		return;
	}
	

	private boolean satisfiedCell(Cell myCell, CellGrid myGrid) {
		int count = 0;
		ArrayList<Cell> neighbourCells = getNeighbours(myCell, myGrid);
		for (Cell neighbour : neighbourCells) {
			if (neighbour.getCurrentState().equals(myCell.getCurrentState())) {
				count++;
			}
		}
		int myT = count / neighbourCells.size();
		return myT > parameters.get("t");
	}

	/*
	 * param @myCell the cell we are finding a new position for param @myGrid
	 * the grid with all the cells
	 * 
	 * PROBLEM: need to make sure I don't pick the same cell as another NEW
	 * isEmpty (canPut?) method to check if current or future states of cells
	 * are not occupying this spot
	 */
	private Cell findAnEmptyValidCell(Cell myCell, CellGrid myGrid) {
		for (int i = 0; i < myGrid.width(); i++) {
			for (int j = 0; i < myGrid.length(); j++) {
				if (myGrid.getCell(i, j).isEmpty()) {
					return myGrid.getCell(i, j);
				}
			}
		}
		return null;
	}

}
