package rule;

import java.util.List;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

public class WatorWorld extends Rule {
	
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		getHashMaps();
		if (myCell.getCurrentType().equals("FISH")) {
			updateFishAndShark(myCell, myGrid, "FISH", "KELP");
		} else if (myCell.getCurrentType().equals("SHARK")) {
			updateFishAndShark(myCell, myGrid, "SHARK", "FISH");
		} else {
			myCell.setNextType("KELP");
		}
	}
	
	@Override
	public void evaluateGrid(CellGrid myGrid) {
		iterateArrayListAndUpdate(myGrid, "SHARK");
		iterateArrayListAndUpdate(myGrid, "FISH");
		iterateArrayListAndUpdate(myGrid, "KELP");
	}

	private void iterateArrayListAndUpdate(CellGrid myGrid, String type) {
		List<Cell> myCells = myGrid.getCellsByType(type);
		for (Cell myCell : myCells) {
			if (myCell.getNextType() != null) {
				evaluateCell(myCell, myGrid);
			}
		}
	}

	void setStatesInMap(Cell myCell){
		myCell.setCurrentState("lifetime", 0);
		
	}
	
	void setColor(Cell myCell) {
		if (myCell.getCurrentType().equals("FISH")) {
			myCell.setFill(Color.BLUE);
		} else if (myCell.getCurrentType().equals("KELP")) {
			myCell.setFill(Color.GREEN);
		} else if (myCell.getCurrentType().equals("SHARK")) {
			myCell.setFill(Color.RED);
		}
	}

	private void updateFishAndShark(Cell myCell, CellGrid myGrid, String attacker, String target) {
		if (nonDiagNeighbours.contains(target)) {
			// the cell it moves to will always be FISH
			Cell nextCell = chooseRandomNeighbourCell(myCell, myGrid, target);
			nextCell.setNextType(attacker);

			// if it has lived the lifetime required it can reproduce
			if (myCell.getCurrentState("lifetime") < parameterMap.get("lifetime")) {
				// set the lifetime to the previous one +1
				nextCell.setCurrentState("lifetime",myCell.getCurrentState("lifetime") + 1);
				myCell.setNextType("KELP");
			} else {
				nextCell.setCurrentState("lifetime",myCell.getCurrentState("lifetime") + 1);
				myCell.setNextType(attacker);
			}
		} else {
			// potentially messy code?
			if (attacker.equals("SHARK") && target.equals("FISH")) {
				updateFishAndShark(myCell, myGrid, "SHARK", "KELP");
			} else {
				myCell.setNextType(attacker);
				myCell.setCurrentState("lifetime", myCell.getCurrentState("lifetime") + 1);
			}
		}
	}

	private Cell chooseRandomNeighbourCell(Cell myCell, CellGrid myGrid, String type) {
		Cell myRandomCell = null;
		// not perfectly random or efficient... need to take another look
		while (myRandomCell == null) {
			double myRandomValue = Math.random();
			if (myRandomValue < 0.25 && nonDiagNeighbours.get(0).getCurrentType().equals(type)) {
				return nonDiagNeighbours.get(0);
			} else if (myRandomValue > 0.25 && myRandomValue < 0.5 && nonDiagNeighbours.get(1).getCurrentType().equals(type)) {
				return nonDiagNeighbours.get(1);
			} else if (myRandomValue > 0.5 && myRandomValue < 0.75 && nonDiagNeighbours.get(2).getCurrentType().equals(type)) {
				return nonDiagNeighbours.get(2);
			} else if (myRandomValue > 0.75 && nonDiagNeighbours.get(3).getCurrentType().equals(type)) {
				return nonDiagNeighbours.get(3);
			}
		}

		return null;
	}

}
