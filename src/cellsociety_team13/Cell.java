package cellsociety_team13;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

// The cell class extends Rectangle which 
// contains the current state of the cell, 
// the next state of the cell, the cell type and 
// any other type of useful information.


public class Cell {
	public String nextState;
	public String currentState;
	private int row;
	private int column;
	private int x;
	private int y;
	private int width;
	private int height;
	public int lifetime;
	public Shape shape;
	public Paint color;
	
	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public Cell(int row, int column, int width, int height, int gridX, int gridY){
		this.setRow(row);
		this.setColumn(column);
		this.width = width;
		this.height = height;
		this.setX(gridX + column*width);
		this.setY(gridY + row*height);
		this.shape = new Rectangle(this.x, this.y, this.width, this.height);
		this.color = Color.GRAY;
	}
	
	public void updateState(){
		this.currentState = this.nextState;
		this.nextState = null;
		if (this.currentState.equals("FIRE")){
			this.color = Color.RED;
		} else if (this.currentState.equals("TREE")){
			this.color = Color.BROWN;
		} else if (this.currentState.equals("EMPTY")){
			this.color = Color.GRAY;
		}
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}
