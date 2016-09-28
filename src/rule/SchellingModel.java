package rule;

import javafx.scene.paint.Color;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

/**
 * SchellingModel moves cells to a random "EMPTY" position on the grid whenever
 * they are not "satisfied" a cell is satisfied when its surrounded by at lead t
 * cells that are the same as itself
 * 
 * @author Lucia Martos
 */

public class SchellingModel extends Rule {
	// NOTE: cannot have cells which are null, they must be "EMPTY"
	/* evaluate cell finds the next state for the current grid and current cell
	 * @param myCell is the current cell being analysed
	 */
	public void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		if (!satisfiedCell(myCell, myGrid)) {
			findAnEmptyValidCell(myCell, myGrid).setNextType(myCell.getCurrentType());
			myCell.setNextType("EMPTY");
		} else {
			myCell.setNextType(myCell.getCurrentType());
		}
		return;
	}

	public void setStatesInMap(Cell myCell) {

	}

	public void setColor(Cell myCell) {
		try {
			if (myCell.getCurrentType().equals("EMPTY")) {
				myCell.setFill(Color.WHITE);
			} else if (myCell.getCurrentType().equals("X")) {
				myCell.setFill(Color.BLACK);
			} else if (myCell.getCurrentType().equals("O")) {
				myCell.setFill(Color.BLUE);
			}
		} catch (NullPointerException npe){ // THIS IS FOR DEBUGGING PURPOSES, DON'T REMOVE YET
			myCell.setFill(Color.RED);
		}
	}

	private boolean satisfiedCell(Cell myCell, CellGrid myGrid) {
		int count = 0;
		if (myCell.getCurrentType().equals("EMPTY")){
			return true;
		}
		for (Cell neighbour : myNeighbours) {
			if (neighbour.getCurrentType().equals(myCell.getCurrentType())) {
				count++;
			}
		}
		double myT = 100.0 * count / myNeighbours.size();
		return myT > getParameter("similar");
	}

	private Cell findAnEmptyValidCell(Cell myCell, CellGrid myGrid) {
		boolean emptyTest = false;
		int numLoops = 0;
		while (!emptyTest){
			int i = (int) (20*Math.random());
			int j = (int) (20*Math.random());
			Cell testCell = myGrid.getCell(i, j);
			String cellCurrentType = testCell.getCurrentType();
			String cellNextType = testCell.getNextType();
			// This needs to be shortened but wasn't working with other methods...
			if ((cellCurrentType == null || cellCurrentType.equals("EMPTY")) && 
					(cellNextType == null || cellNextType.equals("EMPTY"))) {
				return testCell;
			} else if (numLoops > 100){
				break;
			}
			numLoops++;
		}
		return null;
	}

}
