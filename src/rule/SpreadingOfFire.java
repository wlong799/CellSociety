package rule;

import javafx.scene.paint.Color;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

/**
 * SpreadingOfFire is a model in which the fire spreads to a tree in its
 * surroundings with the probability of probFire
 * 
 * @author Lucia Martos
 */
public class SpreadingOfFire extends Rule {
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		double myRandomValue = Math.random();
		if (myCell.getCurrentType().equals("TREE") && nonDiagNeighbours.contains("FIRE")) {
			if (myRandomValue < parameterMap.get("probCatch")) {
				myCell.setNextType("FIRE");
			} else {
				myCell.setNextType("TREE");
			}
		} else if (myCell.getCurrentType().equals("FIRE")) {
			myCell.setNextType("EMPTY");
		}
		// this is in the case the cell is empty it remains empty or if the cell
		// was a try and did not catch fire
		else {
			myCell.setNextType(myCell.getCurrentType());
		}
		return;
	}

	void setStatesInMap(Cell myCell) {
	}

	void setColor(Cell myCell) {
		if (myCell.getCurrentType().equals("FIRE")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("TREE")) {
			myCell.setFill(Color.GREEN);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			myCell.setFill(Color.WHITE);
		}
	}

}
