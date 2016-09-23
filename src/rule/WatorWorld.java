package rule;

import java.util.ArrayList;

import com.sun.prism.paint.Color;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

public class WatorWorld extends Rule {

	// NOTE: MUST UPDATE THE SHARKS POSITION BEFORE THE FISH BECAUSE SHARKS HAVE
	// PREFERENCE!
	void evaluateGrid(CellGrid myGrid){		
		iterateArrayListAndUpdate(myGrid, "SHARK");
		iterateArrayListAndUpdate(myGrid, "FISH");
		iterateArrayListAndUpdate(myGrid, "KELP");

	}

	private void iterateArrayListAndUpdate(CellGrid myGrid, String type) {
		ArrayList<Cell> myCells = myGrid.getCellsWithState(type, myGrid);
		for(Cell myCell : myCells){
			if(){
				evaluateCell(myCell, myGrid);

			}
		}
	}

	void evaluateCell(Cell myCell, CellGrid myGrid) {
		getHashMaps();
		if (myCell.getCurrentState().equals("FISH")) {
			updateFishAndShark(myCell, myGrid, "FISH", "KELP");
		} else if (myCell.getCurrentState().equals("SHARK")) {
			updateFishAndShark(myCell, myGrid, "SHARK", "FISH");
		} else {
			myCell.setNextState("KELP");
		}
	}
	
	void setColor(Cell myCell){
		if(myCell.getCurrentState().equals("FISH")){
			myCell.setFill(Color.BLUE);
		}
		else if(myCell.getCurrentState().equals("KELP")){
			myCell.setFill(Color.GREEN);
		}
		else if(myCell.getCurrentState().equals("SHARK")){
			myCell.setFill(Color.RED);
		}
	}

	private void updateFishAndShark(Cell myCell, CellGrid myGrid, String attacker, String target) {
		if (nonDiagNeighbours.contains(target)) {
			// the cell it moves to will always be FISH
			Cell nextCell = chooseRandomNeighbourCell(myCell, myGrid, target);
			nextCell.setNextState(attacker);

			// if it has lived the lifetime required it can reproduce
			if (myCell.getLifetime() < parameterMap.get("lifetime")) {
				// set the lifetime to the previous one +1
				nextCell.setLifetime(myCell.getLifetime() + 1);
				myCell.setNextState("KELP");
			} else {
				nextCell.setLifetime(myCell.getLifetime() + 1);
				myCell.setNextState(attacker);
			}
		} else {
			// potentially messy code?
			if (attacker.equals("SHARK") && target.equals("FISH")) {
				updateFishAndShark(myCell, myGrid, "SHARK", "KELP");
			} else {
				myCell.setNextState(attacker);
				myCell.setLifetime(myCell.getLifetime() + 1);
			}
		}
	}

	private Cell chooseRandomNeighbourCell(Cell myCell, CellGrid myGrid, String type) {
		getNonDiagNeighbours(myCell, myGrid);
		Cell myRandomCell = null;
		// not perfectly random or efficient... need to take another look
		while (myRandomCell == null) {
			double myRandomValue = Math.random();
			if (myRandomValue < 0.25 && myRightCell.getCurrentState().equals(type)) {
				return myRightCell;
			}
			else if (myRandomValue > 0.25 && myRandomValue < 0.5 && myLeftCell.getCurrentState().equals(type)) {
				return myLeftCell;
			} else if (myRandomValue > 0.5 && myRandomValue < 0.75 && myBottomCell.getCurrentState().equals(type)) {
				return myBottomCell;
			} else if (myRandomValue > 0.75 && myTopCell.getCurrentState().equals(type)) {
				return myTopCell;
			}
		}

		return null;
	}

}
