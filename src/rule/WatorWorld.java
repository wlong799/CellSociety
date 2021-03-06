package rule;

import java.util.List;
import java.util.Random;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * WatorWorld is a simulation in which the sharks eat the fish and the fish eat
 * the kelp, the shark has preference over the fish in eating, if the animal has
 * been alive for more than x amount of time they move and reproduce
 * 
 * @author Lucia Martos
 */
public class WatorWorld extends Rule {

	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		if (myCell.getCurrentType().equals("FISH")) {
			updateFishAndShark(myCell, myGrid, "FISH", "KELP");
		} else if (myCell.getCurrentType().equals("SHARK")) {
			updateFishAndShark(myCell, myGrid, "SHARK", "FISH");
		} else {
			myCell.setNextType("KELP");
		}

	}

	private boolean checkNeighboursForTarget(String target) {
		for (Cell myNeighbour : nonDiagNeighbours) {
			if (myNeighbour.getCurrentType().equals(target)) {
				return true;
			}
		}
		return false;
	}

	private void updateFishAndShark(Cell myCell, CellGrid myGrid, String attacker, String target) {
		boolean containsTarget = checkNeighboursForTarget(target);
		if (containsTarget) {
			handleNeighbourHasTarget(myCell, myGrid, attacker, target);
		} else {
			handleNeighourNoTarget(myCell, myGrid, attacker, target);
		}
	}

	private void handleNeighourNoTarget(Cell myCell, CellGrid myGrid, String attacker, String target) {
		// potentially messy code?
		if (attacker.equals("SHARK") && target.equals("FISH")) {
			updateFishAndShark(myCell, myGrid, "SHARK", "KELP");
		} else {
			myCell.setNextType(attacker);
			myCell.setNextState("lifetime", myCell.getCurrentState("lifetime") + 1);
		}
	}

	private void handleNeighbourHasTarget(Cell myCell, CellGrid myGrid, String attacker, String target) {
		// the cell it moves to will always be FISH
		Cell nextCell = chooseRandomNeighbourCell(myCell, myGrid, target);
		nextCell.setNextType(attacker);
		nextCell.setNextState("lifetime", myCell.getCurrentState("lifetime") + 1);

		// if it has lived the lifetime required it can reproduce
		if (myCell.getCurrentState("lifetime") < getParameter("lifetime")) { 
			// set the lifetime to the previous one +1
			myCell.setNextType("KELP");
		} else {
			myCell.setNextType(attacker);
		}
		myCell.setNextState("lifetime", 0);
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
			if (myCell.getNextType() == null) {
				evaluateCell(myCell, myGrid);
			}
		}
	}

	void setStatesInMap(Cell myCell) {
		myCell.setCurrentState("lifetime", 0);
	}

	public void setColor(Cell myCell, CellGrid myGrid) {
		if (myCell.getCurrentType().equals("FISH")) {
			myCell.setFill(Color.BLUE);
		} else if (myCell.getCurrentType().equals("KELP")) {
			myCell.setFill(Color.GREEN);
		} else if (myCell.getCurrentType().equals("SHARK")) {
			myCell.setFill(Color.RED);
		}
	}

	private Cell chooseRandomNeighbourCell(Cell myCell, CellGrid myGrid, String type) {
		// not perfectly random or efficient... need to take another look
		Random rn = new Random();
		return nonDiagNeighbours.get(rn.nextInt(nonDiagNeighbours.size()-1));
	}

	@Override
	void setBGStatesInMap(BackgroundCell myBGCell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		// TODO Auto-generated method stub
		
	}

}
