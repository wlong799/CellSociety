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

	private Cell[][] cells;
	private Rule rule;
	private boolean isRunning;

	
	public CellGrid(double xPos, double yPos, double drawWidth, double drawHeight,
					int gridWidth, int gridHeight, String[][] initialCellTypes, Rule rule) {
		setLayoutX(xPos);
		setLayoutY(yPos);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		drawCellWidth = drawWidth / gridWidth;
		drawCellHeight = drawHeight / gridHeight;

		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		cells = new Cell[gridHeight][gridWidth];
		for (int r = 0; r < gridHeight; r++) {
			for (int c = 0; c < gridWidth; c++) {
				addCell(initialCellTypes[r][c], r, c);
			}
		}

		this.rule = rule;
	}
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getGridHeight() {
		return gridHeight;
	}

	private void addCell(String cellType, int row, int col) {
		double xPos = col * drawCellWidth;
		double yPos = row * drawCellHeight;
		Cell cell = new Cell(cellType, xPos, yPos, drawCellWidth, drawCellHeight, row, col);
		getChildren().add(cell);
		cells[row][col] = cell;
	}
	
	public List<Cell> getCellsByType(String cellType){
		List<Cell> myCells = new ArrayList<>();
			for(int r = 0; r < gridHeight; r ++){
				for(int c = 0; c < gridWidth; c ++){
					if (cells[r][c].getCurrentType().equals(cellType)) {
						myCells.add(cells[r][c]);
					}
				}
			}
		return myCells;
	}
	
	public Cell getCell(int row, int col){
		return cells[row][col];
	}
	
	public void step(){
		rule.evaluateGrid(this);
	}
	
	public void run() throws InterruptedException{
		this.isRunning = true;
		while (isRunning){
			this.step();
			Thread.sleep(5000);
		}
	}
	
	public Cell getMyRightCell() {
		return myRightCell;
	}
	
	public Cell getMyBottomCell() {
		return myBottomCell;
	}
	
	public Cell getMyTopCell() {
		return myTopCell;
	}
	public Cell getMyLeftCell() {
		return myLeftCell;
	}
	
	public List<Cell> getNonDiagNeighbours(Cell myCell) {
		List<Cell> nonDiagNeighbours = new ArrayList<Cell>();
		if (getCell(myCell.getMyRow() + 1, myCell.getMyCol()) != null) {
			myRightCell = getCell(myCell.getMyRow() + 1, myCell.getMyCol());
			nonDiagNeighbours.add(myRightCell);
		}
		if (getCell(myCell.getMyRow() - 1, myCell.getMyCol()) != null) {
			myBottomCell = getCell(myCell.getMyRow() - 1, myCell.getMyCol());
			nonDiagNeighbours.add(myBottomCell);
		}
		if (getCell(myCell.getMyRow(), myCell.getMyCol() + 1) != null) {
			myTopCell = getCell(myCell.getMyRow(), myCell.getMyCol() + 1);
			nonDiagNeighbours.add(myRightCell);
		}
		if (getCell(myCell.getMyRow(), myCell.getMyCol() - 1) != null) {
			myLeftCell = getCell(myCell.getMyRow(), myCell.getMyCol() - 1);
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
