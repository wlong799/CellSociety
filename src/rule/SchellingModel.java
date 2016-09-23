package rule;

import javafx.scene.paint.Color;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

public class SchellingModel extends Rule {
	//NOTE: cannot have cells which are null, they must be "EMPTY"
	
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		if (!satisfiedCell(myCell, myGrid)) {
			findAnEmptyValidCell(myCell, myGrid).setNextType(myCell.getCurrentType());
			myCell.setNextType("EMPTY");
		}
		return;
	}
	
	void setStatesInMap(Cell myCell){
		
	}
	
	void setColor(Cell myCell){
		if(myCell.getCurrentType().equals("EMPTY")){
			myCell.setFill(Color.WHITE);
		}
		else if(myCell.getCurrentType().equals("X")){
			myCell.setFill(Color.BLACK);
		}
		else if(myCell.getCurrentType().equals("O")){
			myCell.setFill(Color.BLUE);
		}
	}

	private boolean satisfiedCell(Cell myCell, CellGrid myGrid) {
		int count = 0;
		myGrid.getNeighbours(myCell, myGrid);
		for (Cell neighbour : allNeighbours) {
			if (neighbour.getCurrentType().equals(myCell.getCurrentType())) {
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
		for (int i = 0; i < myGrid.getGridWidth(); i++) {
			for (int j = 0; i < myGrid.getGridHeight(); j++) {
				if (myGrid.getCell(i, j).getNextType() == "EMPTY" || myGrid.getCell(i, j).getNextType() == null) {
					return myGrid.getCell(i, j);
				}
			}
		}
		return null;
	}

}
