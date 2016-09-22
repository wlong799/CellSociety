package cellsociety_team13;

import javafx.scene.shape.Rectangle;

// The cell class extends Rectangle which 
// contains the current state of the cell, 
// the next state of the cell, the cell type and 
// any other type of useful information.


public class Cell extends Rectangle {
	public String nextState;
	public String currentState;
	public int row;
	public int column;
	public int lifetime;
	
	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public Cell(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getCurrentState() {
		return currentState;
	}
	
	public void setNextState(String state){
		this.nextState = state;
		this.setLifetime(0); 	//reset lifetime when you change the state
	}
	
	public String getNextState(){
		return nextState;
	}
	
	public boolean isEmpty(){
		return (currentState ==null);
	}
	
	public boolean canPut(){
		if(nextState == null){
			return (currentState =="EMPTY"); 
		}
		return (nextState =="EMPTY");
	}

	public void resetCell(){
		nextState = null;
		currentState = null;
		lifetime = 0; 
	}

}
