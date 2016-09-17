package cellsociety_team13;

import java.util.HashMap;

public abstract class Rule {

	HashMap<String, Integer> states;
	HashMap<String, Integer> parameters; 
	
	abstract void evaluateCell(Cell myCell, CellGrid myGrid);
	
	void getHashMaps(){
		states = new HashMap<String, Integer>(myCellGrid.getStates());
		parameters = new HashMap<String, Integer>(myCellGrid.getParameters());
	}


	
}
