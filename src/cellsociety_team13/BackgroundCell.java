package cellsociety_team13;

import java.util.HashMap;
import java.util.Map;

public class BackgroundCell {

	protected Map<String, Integer> currentBGState, nextBGState;
	protected int myRow, myCol;
	
	
	public Map<String, Integer> getNextBGStateMap() {
		return nextBGState;
	}
	
	public BackgroundCell(int row, int col) {
		myRow = row;
		myCol = col;
		currentBGState = new HashMap<>();
		nextBGState = new HashMap<>();
	}
	
	public int getMyCol() {
		return myCol;
	}

	public int getMyRow() {
		return myRow;
	}
	
	public void stepToNextBGStateAndType() {
		for(String stateName :nextBGState.keySet()){
			int stateVal = nextBGState.get(stateName);
			currentBGState.put(stateName, stateVal);
			nextBGState.put(stateName, null);
		}
	}

	public Integer getCurrentBGState(String myState) {
		return currentBGState.get(myState);
	}
	
	public Integer getNextBGState(String myState){
		return nextBGState.get(myState);
	}
	
	public void setCurrentBGState(String myState, Integer value){
		currentBGState.put(myState,value);
	}
	
	public void setNextBGState(String myState, Integer value){
		nextBGState.put(myState,value);
	}
	
	
}
