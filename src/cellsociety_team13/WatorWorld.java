package cellsociety_team13;

import java.util.ArrayList;


public class WatorWorld extends Rule {

	
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		getHashMaps(); // ask if this is the way it works
		
		while(nonDiagNeighbours.contains("KELP")){
			if(myCell.lifeTime() < parameterMap.get("lifetime")){
				myCell.setNextState() = "FISH";
				Cell nextCell = chooseRandomKelpCell();
				
			}
		}
		
	}

	private Cell chooseRandomKelpCell() {
		double myRandomValue = Math.random();
		ArrayList<Cell> nonDiagNeighbours = getNonDiagNeighbours(myCell, myGrid);

		if(myRandomValue < 0.25){
			return myRightCell;
		}
		return null;
	}
	
	
	
	

}
