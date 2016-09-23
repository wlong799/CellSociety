package cellsociety_team13;

import java.util.ArrayList;

import javafx.scene.Group;

//The cell grid class is simply an array of 
//Cells (obj) which extends Group. CellGrid 
//interacts with the Rule class by making an instance of it. 
//The cell grid will be in charge of calling the for the Rule 
//class to calculate the next state of the cells and then change 
//the state of the cells.

public class CellGrid {
	private int width; // Number of columns
	private int height; // Number of rows
	private int cellWidth;
	private int cellHeight;
	private int x;
	private int y;
	private Rule rule;
	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private boolean run = false;
	
	public CellGrid(int width, int height, int cellWidth, int cellHeight, int x, int y, Rule rule, Group root){
		this.setWidth(width); this.setHeight(height);
		this.cellWidth = cellWidth; this.cellHeight = cellHeight;
		this.rule = rule;
		this.x = x;
		this.y = y;
		for (int row = 0; row < height; row++){
			for (int col = 0; col < width; col++){
				// Based on Rule Type Create Different Cells
				Cell cell = new Cell(col, row, cellWidth, cellHeight, x, y);
				cells.add(cell);
				root.getChildren().add(cell.shape);
			} // End of Width For
		} // End of Length For
	}
	
	public ArrayList<Cell> getCellsWithState(String myType, CellGrid myGrid){
		ArrayList<Cell> myCells = new ArrayList<Cell>();
		
			for(int i = 0; i < myGrid.getWidth(); i ++){
				for(int j = 0; j < myGrid.getHeight(); j ++){
					if(myGrid.getCell(i, j).getCurrentState().equals(myType)){
						myCells.add(myGrid.getCell(i, j));
					}
				}
			}
		return myCells;
	}
	
	public Cell getCell(int column, int row){
		int arrayPos = row*width + column;
		return cells.get(arrayPos);
	}
	
	public void step(){
		rule.evaluateGrid(this);
	}
	
	public void run() throws InterruptedException{
		this.run = true;
		while (run){
			this.step();
			Thread.sleep(5000);
		}
	}

	
	public int getWidth() {return width; }
	public void setWidth(int width) {this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int length) {this.height = length;}

	
	
	
	
//	// User inputs filename and hits submit button
//	// Method in CellGrid...
//	void newGame(String filename) {
//	    // Assume XMLGameReader parses the file properly
//	    XMLGameReader gameReader = new XMLGameReader(filename);
//	    setSize(gameReader.getGameWidth(), gameReader.getGameHeight());
//	    setRule(gameReader.getRuleName());
//	    Map<String, Integer> params = gameReader.getInitialParameters();
//	    for (String name : params.keySet()) {
//	        getRule().setParameter(name, params.get(name));
//	    }
//	    initializeCells(gameReader.getInitialCellStates());
//	}
	
	
//	// User has adjusted value of parameter probCatch to int val through UI slider
//	cellGrid.getRule().setParameter("probCatch", value);
//
//	// And the relevant method in Rule...
//	setParameter(String name, int value) {
//	    parameterMap.put(name, value);
//	}
	
	
//	// Assume that a game has been properly loaded from XML file
//	// User triggers update through Next or Run button
//	cellGrid.update();
//	// And within CellGrid...
//	void update() {
//	    for (Cell cell : cellGrid) {
//	        rule.evaluate(cell, cellGrid);
//	    }
//	    for (Cell cell : cellGrid) {
//	        cell.updateNextState();
//	    }
//	    drawCells();
//	}
	
	
//	// User has adjusted value of parameter probCatch to int val through UI slider
//	cellGrid.getRule().setParameter("probCatch", value);
//
//	// And the relevant method in Rule...
//	setParameter(String name, int value) {
//	    parameterMap.put(name, value);
//	}
	
}
