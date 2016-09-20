package cellsociety_team13;

import java.util.ArrayList;

import javafx.scene.control.Cell;

public class SpreadingOfFire extends Rule {

	
	void evaluateCell(Cell<T> myCell, CellGrid myGrid) {
		double myRandomValue = Math.random();
		ArrayList<Cell> nonDiagNeighbours = getNonDiagNeighbours(myCell, myGrid);

		if (myCell.getCurrentState().equals("TREE") && nonDiagNeighbours.contains("FIRE")) {
			if (myRandomValue < parameters.get("probCatch")) {
				myCell.setNextState = "FIRE";
			} else {
				myCell.setNextState = "TREE";
			}
		}
		else if(myCell.getCurrentState().equals("FIRE")){
			myCell.setNextState = "EMPTY";
		}
		//this is in the case the cell is empty it remains empty or if the cell was a try and did not catch fire
		else{
			myCell.setNextState = myCell.getCurrentState();
		}
		return;
	}
	
	

}
