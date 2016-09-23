package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import rule.Rule;


/**
 * CellGrid is responsible for holding the current Cells within the Cell Society
 * game. It stores the position of the cells and uses the specified Rule class
 * to update the Cells when specified.
 */

public class CellGrid extends Group {
	private double drawWidth, drawHeight;
	private double drawCellWidth, drawCellHeight;

	private int gridWidth, gridHeight;

	private List<Cell> cells = new ArrayList<Cell>();
	private Rule rule;
	private boolean isRunning;

	
	public CellGrid(double xPos, double yPos, double drawWidth, double drawHeight,
					int gridWidth, int gridHeight, List<String> initialCellTypes, Rule rule) {
		setLayoutX(xPos);
		setLayoutY(yPos);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		drawCellWidth = drawWidth / gridWidth;
		drawCellHeight = drawHeight / gridHeight;

		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		
		for (int row = 0; row < gridHeight; row++){
 			for (int col = 0; col < gridWidth; col++){
 				int arrayPos = row*gridWidth + col;
 				// Based on Rule Type Create Different Cells
 				Cell cell = new Cell(initialCellTypes.get(arrayPos), xPos, yPos, drawCellWidth, drawCellHeight, row, col);
 				cells.add(cell);
 				getChildren().add(cell);
 			} // End of Width For
 		} // End of Length For

		this.rule = rule;
	}
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getGridHeight() {
		return gridHeight;
	}
	
	public List<Cell> getCellsByType(String cellType){
		List<Cell> myCells = new ArrayList<>();
		for (Cell cell : cells){
			if (cell.getCurrentType().equals(cellType)){
				myCells.add(cell);
			}
		}
		return myCells;
	}
	
	public Cell getCell(int row, int col){
		if ((col >= gridWidth || (col < 0)) || (row >= gridHeight) || (row < 0)){
			return null;
		}
		else {
			int arrayPos = row*gridWidth + col;
	 		return cells.get(arrayPos);
		}
		
	}
	
	public void step(){
		rule.evaluateGrid(this);
	}
	
	public List<Cell> getNonDiagNeighbours(Cell myCell) {
		List<Cell> nonDiagNeighbours = new ArrayList<Cell>();
		if (getCell(myCell.getMyRow() + 1, myCell.getMyCol()) != null) {
			Cell myRightCell = getCell(myCell.getMyRow() + 1, myCell.getMyCol());
			nonDiagNeighbours.add(myRightCell);
		}
		if (getCell(myCell.getMyRow() - 1, myCell.getMyCol()) != null) {
			Cell myBottomCell = getCell(myCell.getMyRow() - 1, myCell.getMyCol());
			nonDiagNeighbours.add(myBottomCell);
		}
		if (getCell(myCell.getMyRow(), myCell.getMyCol() + 1) != null) {
			Cell myTopCell = getCell(myCell.getMyRow(), myCell.getMyCol() + 1);
			nonDiagNeighbours.add(myTopCell);
		}
		if (getCell(myCell.getMyRow(), myCell.getMyCol() - 1) != null) {
			Cell myLeftCell = getCell(myCell.getMyRow(), myCell.getMyCol() - 1);
			nonDiagNeighbours.add(myLeftCell);
		}
		return nonDiagNeighbours;
	}

	public List<Cell> getNeighbours(Cell myCell) {
		List<Cell> myNeighbours = new ArrayList<Cell>(getNonDiagNeighbours(myCell));
		if (getCell(myCell.getMyRow() + 1, myCell.getMyCol() + 1) != null) {
			myNeighbours.add(getCell(myCell.getMyRow() + 1, myCell.getMyCol() + 1));
		}
		if (getCell(myCell.getMyRow() + 1, myCell.getMyCol() - 1) != null) {
			myNeighbours.add(getCell(myCell.getMyRow() + 1, myCell.getMyCol() - 1));
		}
		if (getCell(myCell.getMyRow() - 1, myCell.getMyCol() - 1) != null) {
			myNeighbours.add(getCell(myCell.getMyRow() - 1, myCell.getMyCol() - 1));
		}
		if (getCell(myCell.getMyRow() - 1, myCell.getMyCol() + 1) != null) {
			myNeighbours.add(getCell(myCell.getMyRow() - 1, myCell.getMyCol() + 1));
		}
		return myNeighbours;
	}
}
