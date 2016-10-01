package cellsociety_team13;

import java.util.Map;

public class BackgroundCell {

	protected Map<String, Integer> currentBackgroundState, nextBackgroundState;

	protected int myRow, myCol;
	
	public BackgroundCell() {

	}

	public Integer getCurrentBackgroundState(String myState) {
		return currentBackgroundState.get(myState);
	}
	
	public Integer getNextBackgroundState(String myState){
		return nextBackgroundState.get(myState);
	}
	
	public void setNextBackgroundState(String myState, Integer value){
		nextBackgroundState.put(myState,value);
	}
	
	
}
