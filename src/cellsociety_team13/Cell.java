package cellsociety_team13;

import javafx.scene.shape.Rectangle;

// The cell class extends Rectangle which 
// contains the current state of the cell, 
// the next state of the cell, the cell type and 
// any other type of useful information.


public class Cell extends Rectangle {
	public String setNextState;
	public int row;
	public int column;
	
	public Cell(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public void setNextState(){};
//	if (myGrid.getCell(i, j).isEmpty()) {
//		return myGrid.getCell(i, j);


	public Object getCurrentState() {
		return null;
	}

}
