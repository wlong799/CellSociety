package cellsociety_team13;

public class SchellingModel extends Rule {
	//NOTE:: cannot have cells which are null, they must be "EMPTY"
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		if (!satisfiedCell(myCell, myGrid)) {
			myCell.setNextState("T O WHAAT");
			findAnEmptyValidCell(myCell, myGrid).setNextState("EMPTY");
		}
		return;
	}
	

	private boolean satisfiedCell(Cell myCell, CellGrid myGrid) {
		int count = 0;
		getNeighbours(myCell, myGrid);
		for (Cell neighbour : allNeighbours) {
			if (neighbour.getCurrentState().equals(myCell.getCurrentState())) {
				count++;
			}
		}
		int myT = count / allNeighbours.size();
		return myT > parameterMap.get("t");
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
		for (int i = 0; i < myGrid.getWidth(); i++) {
			for (int j = 0; i < myGrid.getHeight(); j++) {
				if (myGrid.getCell(i, j).canPut()) {
					return myGrid.getCell(i, j);
				}
			}
		}
		return null;
	}

}
